import React, { useState, useEffect } from 'react';
import axios from 'axios';

const LoanList = () => {
    const [loans, setLoans] = useState([]);
    const [filters, setFilters] = useState({
        date: '',
        state: '',
        grade: '',
        ficoLow: '',
        ficoHigh: '',
        sortBy: '',
        order: 'asc',
        limit: 100
    });

    const fetchLoans = async () => {
        console.log('Fetching loans with filters:', filters); // Logging filters
        try {
            const response = await axios.get('http://localhost:8080/loans', { params: filters });
            console.log('Loans fetched:', response.data); // Logging response
            setLoans(response.data);
        } catch (error) {
            console.error('Error fetching loans:', error);
        }
    };

    useEffect(() => {
        // Fetching loans with default filters on first load
        fetchLoans();
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFilters({
            ...filters,
            [name]: value
        });
    };

    return (
        <div className="container">
            <h1>Loan List</h1>
            <div className="filter-section">
                <label>
                    Date:
                    <input type="date" name="date" value={filters.date} onChange={handleInputChange} />
                </label>
                <label>
                    State:
                    <input type="text" name="state" value={filters.state} onChange={handleInputChange} />
                </label>
                <label>
                    Grade:
                    <input type="text" name="grade" value={filters.grade} onChange={handleInputChange} />
                </label>
                <label>
                    FICO Low:
                    <input type="number" name="ficoLow" value={filters.ficoLow} onChange={handleInputChange} />
                </label>
                <label>
                    FICO High:
                    <input type="number" name="ficoHigh" value={filters.ficoHigh} onChange={handleInputChange} />
                </label>
                <label>
                    Sort By:
                    <select name="sortBy" value={filters.sortBy} onChange={handleInputChange}>
                        <option value="">Select</option>
                        <option value="date">Date</option>
                        <option value="state">State</option>
                        <option value="grade">Grade</option>
                        <option value="fico">FICO</option>
                    </select>
                </label>
                <label>
                    Order:
                    <select name="order" value={filters.order} onChange={handleInputChange}>
                        <option value="asc">Ascending</option>
                        <option value="desc">Descending</option>
                    </select>
                </label>
                <label>
                    Limit:
                    <input type="number" name="limit" value={filters.limit} onChange={handleInputChange} />
                </label>
                <button onClick={fetchLoans}>Filter</button>
            </div>
            <div className="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Grade</th>
                        <th>State</th>
                        <th>Issue Date</th>
                        <th>FICO Range Low</th>
                    </tr>
                    </thead>
                    <tbody>
                    {loans.map((loan) => (
                        <tr key={loan.id}>
                            <td>{loan.id}</td>
                            <td>{loan.grade}</td>
                            <td>{loan.addrState}</td>
                            <td>{loan.issueDate}</td>
                            <td>{loan.ficoRangeLow}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default LoanList;
