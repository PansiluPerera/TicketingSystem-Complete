import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './vendor-dashboard.css';
import Header from './Header'; 

function VendorDashboard() {
  const navigate = useNavigate();
  const [vendors, setVendors] = useState([]);
  const [events, setEvents] = useState([]);
  const [selectedEventId, setSelectedEventId] = useState(null);
  const [totalTickets, setTotalTickets] = useState(0);
  const [vendorId, setVendorId] = useState(1); // Vendor ID 
  const [error, setError] = useState(null);

  useEffect(() => {

    const fetchVendorsAndEvents = async () => {
      try {
        const vendorsResponse = await axios.get('http://localhost:8080/api/vendors/getVendors');
        setVendors(vendorsResponse.data);

        const eventsResponse = await axios.get('http://localhost:8080/api/event/all');
        setEvents(eventsResponse.data);
      } catch (err) {
        setError("Failed to load vendors or events.");
      }
    };
    fetchVendorsAndEvents();
  }, []);

  const handleTicketSubmit = async (e) => {
    e.preventDefault();
    const requestBody = { totalTickets };

    try {
      const response = await axios.post(`http://localhost:8080/api/vendors/${vendorId}/events/${selectedEventId}`, requestBody, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.status === 200) {
        alert("Tickets updated successfully!");
      }
    } catch (error) {
      setError("Failed to update tickets. Please try again.");
    }
  };

  return (
    <div className="vendor-dashboard">

      <Header /> 

      <br></br>
      <h2>Vendor Dashboard</h2>

      {error && <div style={{ color: 'red' }}>{error}</div>}

      {/* Vendors Table */}
      <div className="vendors-list">
        <h3>Registered Vendors</h3>
        <table>
          <thead>
            <tr>
              <th>Vendor ID</th>
              <th>Vendor Name</th>
            </tr>
          </thead>
          <tbody>
            {vendors.map(vendor => (
              <tr key={vendor.vendorID}>
                <td>{vendor.vendorID}</td>
                <td>{vendor.vendorName}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Events Table */}
      <div className="events-list">
        <h3>Available Events</h3>
        <table>
          <thead>
            <tr>
              <th>Event ID</th>
              <th>Event Name</th>
              <th>Description</th>
              <th>Max Ticket Capacity</th>
            </tr>
          </thead>
          <tbody>
            {events.map(event => (
              <tr key={event.eventid}>
                <td>{event.eventid}</td>
                <td>{event.event_name}</td>
                <td>{event.event_description}</td>
                <td>{event.maxTicketCapacity}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Form for Setting Tickets for Selected Event */}
      <div className="ticket-form">
        <h3>Set Total Tickets for Event</h3>
        <form onSubmit={handleTicketSubmit}>
          {/* Vendor ID Input */}
          <label htmlFor="vendorId">
            Vendor ID:
            <input
              id="vendorId"
              type="number"
              value={vendorId}
              onChange={(e) => setVendorId(e.target.value)}
              required

            />
          </label>

          {/* Event Selection Dropdown */}
          <label htmlFor="eventSelect">
            Select Event:
            <select
              id="eventSelect"
              value={selectedEventId || ''}
              onChange={(e) => setSelectedEventId(e.target.value)}
              required
            >
              <option value="">-- Select an Event --</option>
              {events.map(event => (
                <option key={event.eventid} value={event.eventid}>
                  {event.event_name}
                </option>
              ))}
            </select>
          </label>

          {/* Total Tickets Input */}
          <label htmlFor="totalTickets">
            Total Tickets:
            <input
              id="totalTickets"
              type="number"
              value={totalTickets}
              onChange={(e) => setTotalTickets(e.target.value)}
              min="0"
              required
            />
          </label>


          <button type="submit">Submit</button>
        </form>
      </div>
    </div>
  );
}

export default VendorDashboard;
