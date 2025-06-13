import React, { useState, useEffect } from 'react';
import './admin-dashboard.css';
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, LineElement, PointElement, Title, Tooltip, Legend } from 'chart.js';
import Header from './Header'; 

ChartJS.register(CategoryScale, LinearScale, LineElement, PointElement, Title, Tooltip, Legend);


function AdminDashboard() {
  const [selectedTab, setSelectedTab] = useState('addEvent');
  const [eventName, setEventName] = useState('');
  const [eventDescription, setEventDescription] = useState('');
  const [maxTicketCapacity, setMaxTicketCapacity] = useState(0);
  const [ticketReleaseRate, setTicketReleaseRate] = useState(0);
  const [customerRetrievalRate, setCustomerRetrievalRate] = useState(0);
  const [confirmationMessage, setConfirmationMessage] = useState('');

  const [vendors, setVendors] = useState([]);
  const [customers, setCustomers] = useState([]);
  const [events, setEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null);

  const [ticketCountPerVendor, setTicketCountPerVendor] = useState({});
  const [ticketPurchases, setTicketPurchases] = useState([]);

  
  
  useEffect(() => {
    if (selectedTab === 'startSales' && selectedEvent) {

      fetchTicketCountPerVendor(selectedEvent.eventid);
      fetchTicketPurchases(selectedEvent.eventid);
  
      // Polling every 3 seconds
      const interval = setInterval(() => {
        fetchTicketCountPerVendor(selectedEvent.eventid);
        fetchTicketPurchases(selectedEvent.eventid);
      }, 3000); // 3 seconds polling rate
  

      return () => clearInterval(interval);
    }
  }, [selectedTab, selectedEvent]);
  
  const fetchTicketCountPerVendor = async (eventId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/event/${eventId}/ticket-count-per-vendor`);
      if (response.ok) {
        const data = await response.json();
        setTicketCountPerVendor(data);
      } else {
        console.error('Failed to fetch ticket count per vendor');
      }
    } catch (error) {
      console.error('An error occurred while fetching ticket count per vendor:', error);
    }
  };
  
  const fetchTicketPurchases = async (eventId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/event/${eventId}/ticket-purchases`);
      if (response.ok) {
        const data = await response.json();
        setTicketPurchases(data);
      } else {
        console.error('Failed to fetch ticket purchases');
      }
    } catch (error) {
      console.error('An error occurred while fetching ticket purchases:', error);
    }
  };

  
  useEffect(() => {
    if (selectedTab === 'vendorInfo') {
      fetchVendors();
    }

    if (selectedTab === 'customerInfo') {
      fetchCustomers();
    }

    if (selectedTab === 'startSales') {
      fetchEvents();
    }
  }, [selectedTab]);

  const fetchVendors = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/vendors/getVendors');
      if (response.ok) {
        const data = await response.json();
        setVendors(data);
      } else {
        console.error('Failed to fetch vendors');
      }
    } catch (error) {
      console.error('An error occurred while fetching vendors:', error);
    }
  };

  const fetchCustomers = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/user/all');
      if (response.ok) {
        const data = await response.json();
        setCustomers(data);
      } else {
        console.error('Failed to fetch customers');
      }
    } catch (error) {
      console.error('An error occurred while fetching customers:', error);
    }
  };

  const fetchEvents = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/event/all');
      if (response.ok) {
        const data = await response.json();
        setEvents(data);
      } else {
        console.error('Failed to fetch events');
      }
    } catch (error) {
      console.error('An error occurred while fetching events:', error);
    }
  };

  const handleEventClick = (event) => {
    setSelectedEvent(event);
  };

  const startSales = async () => {
    if (!selectedEvent) return;

    try {
      const response = await fetch(`http://localhost:8080/api/admin/startSales?eventId=${selectedEvent.eventid}`, {
        method: 'POST',
      });

      if (response.ok) {
        alert('Sales started successfully for the event!');
      } else {
        alert('Failed to start sales. Please try again.');
      }
    } catch (error) {
      alert('An error occurred while starting sales: ' + error.message);
    }
  };

  const stopSales = async () => {
    if (!selectedEvent || !selectedEvent.eventid) {
      console.error('Event ID is not set');
      return;
    }
  
    console.log('Stopping sales for Event ID:', selectedEvent.eventid);
  
    try {
      const response = await fetch(
        `http://localhost:8080/api/admin/stopSales?eventId=${selectedEvent.eventid}`,
        {
          method: 'POST',
        }
      );
  
      if (response.ok) {
        alert('Sales stopped successfully for the event!');
      } else {
        alert('Failed to stop sales. Please try again.');
      }
    } catch (error) {
      console.error('An error occurred while stopping sales:', error);
      alert('An error occurred while stopping sales: ' + error.message);
    }
  };
  

  const renderTicketCountGraph = () => {
    const vendorIds = Object.keys(ticketCountPerVendor);
    const ticketCounts = vendorIds.map((vendorId) => ticketCountPerVendor[vendorId]);
  
    const data = {
      labels: vendorIds, 
      datasets: [
        {
          label: 'Tickets Sold',
          data: ticketCounts, 
          fill: false,
          borderColor: 'rgb(75, 192, 192)',
          tension: 0.1,
        },
      ],
    };
  
    return <Line data={data} />;
  };

  const renderTicketPurchasesTable = () => {
    return (
      <table>
        <thead>
          <tr>
            <th>Ticket ID</th>
            <th>Vendor ID</th>
            <th>User ID</th>
            <th>Purchase Date</th>
          </tr>
        </thead>
        <tbody>
          {ticketPurchases.map((purchase) => (
            <tr key={purchase.ticketID}>
              <td>{purchase.ticketID}</td>
              <td>{purchase.vendor.vendorID}</td>
              <td>{purchase.user.userID}</td>
              <td>{purchase.purchaseDate}</td>
            </tr>
          ))}
        </tbody>
      </table>
    );
  };
  

  const handleSubmit = async (e) => {
    e.preventDefault();

    const eventData = {
      eventId: 0,
      event_name: eventName,
      event_description: eventDescription,
      maxTicketCapacity: parseInt(maxTicketCapacity, 10),
      ticketReleaseRate: parseInt(ticketReleaseRate, 10),
      customerRetrievalRate: parseInt(customerRetrievalRate, 10),
    };

    try {
      const response = await fetch('http://localhost:8080/api/admin/create-event', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(eventData),
      });

      if (response.ok) {
        setConfirmationMessage('Event created successfully!');
      } else {
        setConfirmationMessage('Failed to create event. Please try again.');
      }
    } catch (error) {
      setConfirmationMessage('An error occurred: ' + error.message);
    }
  };

  const renderContent = () => {
    switch (selectedTab) {
      case 'addEvent':
        return (
          <div className="tab-section">
            <h3><b>Add Event</b></h3>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label><b>Event Name</b></label>
                <input
                  type="text"
                  value={eventName}
                  onChange={(e) => setEventName(e.target.value)}
                  required
                  placeholder='Enter event name here'
                />
              </div>
              <div className="form-group">
                <label><b>Event Description</b></label>
                <input
                  type="text"
                  value={eventDescription}
                  onChange={(e) => setEventDescription(e.target.value)}
                  required
                  placeholder='Enter event description here'
                />
              </div>
              <div className="form-group">
                <label><b>Max Ticket Capacity</b></label>
                <input
                  type="number"
                  value={maxTicketCapacity}
                  onChange={(e) => setMaxTicketCapacity(e.target.value)}
                  required
                />
              </div>
              <div className="form-group">
                <label><b>Ticket Release Rate (in seconds)</b></label>
                <input
                  type="number"
                  value={ticketReleaseRate}
                  onChange={(e) => setTicketReleaseRate(e.target.value)}
                  required
                />
              </div>
              <div className="form-group">
                <label><b>Customer Retrieval Rate (in seconds)</b></label>
                <input
                  type="number"
                  value={customerRetrievalRate}
                  onChange={(e) => setCustomerRetrievalRate(e.target.value)}
                  required
                />
              </div>
              <button type="submit">Submit</button>
            </form>
            <br></br>
            {confirmationMessage && <h3>{confirmationMessage}</h3>}
          </div>
        );
        case 'startSales':
          if (!selectedEvent) {
            return (
              <div className="tab-section">
                <h3><b>Events</b></h3>
                <div className="event-grid">
                  {events.map((event) => (
                    <div
                      key={event.eventid}
                      className="event-card"
                      onClick={() => handleEventClick(event)}
                    >
                      <h4>{event.event_name}</h4>
                      <p>{event.event_description}</p>
                    </div>
                  ))}
                </div>
              </div>
            );
          }
          return (
            <div className="tab-section">
              <h3><b>Event Details</b></h3>
              <button className="go-back-button" onClick={() => setSelectedEvent(null)}>&larr; Go Back</button>
              <table>
                <tbody>
                  <tr>
                    <td>Event ID</td>
                    <td>{selectedEvent.eventid}</td>
                  </tr>
                  <tr>
                    <td>Event Name</td>
                    <td>{selectedEvent.event_name}</td>
                  </tr>
                  <tr>
                    <td>Event Description</td>
                    <td>{selectedEvent.event_description}</td>
                  </tr>
                  <tr>
                    <td>Max Ticket Capacity</td>
                    <td>{selectedEvent.maxTicketCapacity}</td>
                  </tr>
                  <tr>
                    <td>Ticket Release Rate</td>
                    <td>{selectedEvent.ticketReleaseRate}</td>
                  </tr>
                  <tr>
                    <td>Customer Retrieval Rate</td>
                    <td>{selectedEvent.customerRetrievalRate}</td>
                  </tr>
                </tbody>
              </table>
              <br></br>
              <button onClick={startSales}>Start Sales</button>
              <br></br>
              <button onClick={stopSales}>Stop Sales</button>

              <br></br>
    
              
              <div>
                <br></br>
                <br></br>
                <h4>Ticket Sales by Vendor</h4>
                {renderTicketCountGraph()}

                <br></br>
                <h4>Ticket Purchases</h4>
                {renderTicketPurchasesTable()}
              </div>
            </div>
          );


      case 'vendorInfo':
        return (
          <div className="tab-section">
            <h3><b>Vendor Information</b></h3>
            <table>
              <thead>
                <tr>
                  <th>Vendor ID</th>
                  <th>Vendor Name</th>
                  <th>Vendor Username</th>
                </tr>
              </thead>
              <tbody>
                {vendors.map((vendor) => (
                  <tr key={vendor.vendorID}>
                    <td>{vendor.vendorID}</td>
                    <td>{vendor.vendorName}</td>
                    <td>{vendor.vendorUserName}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        );
      case 'customerInfo':
        return (
          <div className="tab-section">
            <h3><b>Customer Information</b></h3>
            <table>
              <thead>
                <tr>
                  <th>Customer ID</th>
                  <th>Customer Name</th>
                </tr>
              </thead>
              <tbody>
                {customers.map((customer) => (
                  <tr key={customer.userID}>
                    <td>{customer.userID}</td>
                    <td>{customer.userName}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        );
      default:
        return <div className="tab-section">Select a Tab</div>;
    }
  };

  return (
    <div className="admin-dashboard">
      <Header /> 
      <br></br>

      <h2>Admin Dashboard</h2>
      <nav className="navbar">
        <button
          className={`tab-button ${selectedTab === 'addEvent' ? 'active-tab' : ''}`}
          onClick={() => setSelectedTab('addEvent')}
        >
          Add Event
        </button>
        <button
          className={`tab-button ${selectedTab === 'startSales' ? 'active-tab' : ''}`}
          onClick={() => setSelectedTab('startSales')}
        >
          Start Sales
        </button>
        <button
          className={`tab-button ${selectedTab === 'vendorInfo' ? 'active-tab' : ''}`}
          onClick={() => setSelectedTab('vendorInfo')}
        >
          Vendor Information
        </button>
        <button
          className={`tab-button ${selectedTab === 'customerInfo' ? 'active-tab' : ''}`}
          onClick={() => setSelectedTab('customerInfo')}
        >
          Customer Information
        </button>
      </nav>
      <div className="tab-content">{renderContent()}</div>
    </div>
  );
}

export default AdminDashboard;
