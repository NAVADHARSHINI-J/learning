import { useEffect, useState } from 'react';
import './Practice.css'
import axios from 'axios';

function EmployeeList() {
    const [employees, setEmployees] = useState([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(2);
    const [pageArray, setPageArray] = useState([]);
    const [totalpage, setTotalpage] = useState(0);
    const [department, setDepartment] = useState([]);
    const [departmentId, setDepartmentId] = useState();
    const [selectedEmployee, setSelectedEmployee] = useState({});
    //useeffect
    useEffect(() => {
        const getEmployees = async () => {
            //call the api
            try {
                const response = await axios.get(`http://localhost:8081/api/employee/getall?page=${page}&size=${size}`);
                setEmployees(response.data.list);
                setTotalpage(response.data.totalPages);
                let tp = response.data.totalPages;
                let temp = []
                while (tp > 0) {
                    temp.push(1);
                    tp = tp - 1;
                }
                setPageArray(temp);
            }
            catch (err) {
                console.log(err);
            }
        }
        const getDepartment = async () => {
            try {
                const response = await axios.get("http://localhost:8081/api/department/getall");
                setDepartment(response.data);
            } catch (error) {
                console.log(error);
            }
        }
        getEmployees();
        getDepartment();
    }, [page])

    //add the employee
    const addEmployee = async (employee, departId) => {
        try {
            const response = await axios
                .post(`http://localhost:8081/api/employee/add-employee/${departId}`, employee)
            console.log(employee);
            //add it to the list
            let temp = [...employees];
            temp.push(response.data);
            setEmployees(temp);
        } catch (error) {
            console.log(error);
        }
    }

    //edit the employee
    const editEmployee = (employee) => {
        console.log(employee);
    }
    return (
        <div>
            <div className="container-fluid">
                <div className="row">
                    {/* <!-- Sidebar Component --> */}
                    {/* <Sidebar /> */}
                    {/* <!-- Main Content Area --> */}
                    <div className="col-md-10 p-4">
                        <div className="card">
                            <div className="card-header d-flex justify-content-between align-items-center">
                                <span>Employee List</span>
                                <div>
                                    <input type="text" className="form-control form-control-sm me-2 d-inline-block" style={{ width: 200 }} placeholder="Search employees..." />
                                    <button className="btn btn-light btn-sm" data-bs-toggle="modal" data-bs-target="#addEmployeeModal">
                                        <i className="bi bi-plus me-2"></i>Add Employee
                                    </button>
                                </div>
                            </div>
                            <div className="card-body">
                                <div className="row">
                                    {employees.sort((a, b) => a.id - b.id).map((e, index) => (
                                        <div className="col-md-4 mb-3">
                                            <div className="card employee-card hover-effect" key={index}>
                                                <div className="card-body">
                                                    <div className="d-flex justify-content-between mb-3">
                                                        <h5 className="card-title">{e.name}</h5>
                                                        <span className="badge bg-primary">{e.department.name}</span>
                                                    </div>
                                                    <p className="card-text">
                                                        <i className="bi bi-envelope me-2"></i>{e.email}<br />
                                                        <i className="bi bi-telephone me-2"></i>{e.contact}
                                                    </p>
                                                    <div className="d-flex justify-content-between">
                                                        <button className="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#viewEmployeeModal">
                                                            <i className="bi bi-eye"></i>
                                                        </button>
                                                        <button className="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#editEmployeeModal"
                                                            onClick={() => { setSelectedEmployee(e) }}>
                                                            <i className="bi bi-pencil"></i>
                                                        </button>
                                                        <button className="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal">
                                                            <i className="bi bi-trash"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            </div>
                            <div className="card-footer">
                                <div className="row">
                                    <div className="col-sm-4"></div>
                                    <div className="col-sm-6">
                                        <nav aria-label="Page navigation">
                                            <ul class="pagination">
                                                {
                                                    page === 0 ? <li className="page-item disabled">
                                                        <a className="page-link" href="#" tabIndex="-1" aria-disabled="true">Prev</a>
                                                    </li> : <li className="page-item">
                                                        <a className="page-link" href="#" onClick={() => { setPage(page - 1) }}>Prev</a>
                                                    </li>
                                                }
                                                {pageArray.map((e, index) => (
                                                    <li key={index}
                                                        className={`page-item ${page === index ? "active" : ""}`}>
                                                        <a class="page-link" href="#"
                                                            onClick={() => { setPage(index) }}>{index + 1}</a></li>
                                                ))}
                                                {
                                                    page === totalpage - 1 ? <li class="page-item disabled">
                                                        <a class="page-link" href="#" aria-disabled="true">Next</a>
                                                    </li> : <li class="page-item">
                                                        <a class="page-link" href="#" onClick={() => { setPage(page + 1) }}>Next</a>
                                                    </li>
                                                }
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {/* <!-- New Asset Details Modal --> */}
                        <div className="modal fade" id="assetDetailsModal" tabIndex="-1">
                            <div className="modal-dialog modal-lg">
                                <div className="modal-content">
                                    <div className="modal-header">
                                        <h5 className="modal-title">Asset Details</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <div className="row">
                                            <div className="col-md-6">
                                                <h6 className="detail-label">Asset Information</h6>
                                                <p><strong>Asset Name:</strong> MacBook Pro 16"</p>
                                                <p><strong>Asset Number:</strong> Asset #1234</p>
                                                <p><strong>Model:</strong> MacBook Pro 16-inch, M1 Pro</p>
                                                <p><strong>Purchase Date:</strong> February 1, 2023</p>
                                            </div>
                                            <div className="col-md-6">
                                                <h6 className="detail-label">Allocation Details</h6>
                                                <p><strong>Allocation Date:</strong> February 1, 2023</p>
                                                <p><strong>Quantity:</strong> 1</p>
                                            </div>
                                        </div>
                                        <div className="row mt-3">
                                            <div className="col-12">
                                                <h6 className="detail-label">Additional Specifications</h6>
                                                <ul>
                                                    <li>Processor: Apple M1 Pro</li>
                                                    <li>RAM: 16GB</li>
                                                    <li>Storage: 512GB SSD</li>
                                                    <li>Screen: 16-inch Liquid Retina XDR display</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {/* <!-- New Asset Return Modal --> */}
                        <div className="modal fade" id="assetReturnModal" tabIndex="-1">
                            <div className="modal-dialog">
                                <div className="modal-content">
                                    <div className="modal-header bg-warning">
                                        <h5 className="modal-title">Asset Return Request</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <div className="mb-3">
                                            <label className="form-label">Asset Details</label>
                                            <input type="text" className="form-control" value="MacBook Pro 16" readOnly />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label">Return Reason</label>
                                            <select className="form-select">
                                                <option>Select Reason</option>
                                                <option>Asset Replacement</option>
                                                <option>End of Project</option>
                                                <option>Employee Transfer</option>
                                                <option>Asset Repair</option>
                                                <option>Other</option>
                                            </select>
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label">Comments</label>
                                            <textarea className="form-control" rows="3" placeholder="Additional details about the return"></textarea>
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label">Estimated Return Date</label>
                                            <input type="date" className="form-control" />
                                        </div>
                                        <div className="form-check">
                                            <input className="form-check-input" type="checkbox" id="confirmReturn" />
                                            <label className="form-check-label" >
                                                I confirm the asset is in good condition
                                            </label>
                                        </div>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="button" className="btn btn-warning" disabled id="submitReturnBtn">Submit Return Request</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {/* <!-- View Employee Modal --> */}
                        <div className="modal fade" id="viewEmployeeModal" tabIndex="-1">
                            <div className="modal-dialog modal-lg">
                                <div className="modal-content">
                                    <div className="modal-header">
                                        <h5 className="modal-title">Employee Details</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <div className="row">
                                            <div className="col-md-8">
                                                <h4>John Doe</h4>
                                                <p><span className="detail-label">Id:</span> 12</p>
                                                <p><span className="detail-label">Email:</span> john.doe@hexaassets.com</p>
                                                <p><span className="detail-label">Phone:</span> +1 (555) 123-4567</p>
                                                <p><span className="detail-label">Department:</span> IT</p>
                                                <p><span className="detail-label">Address:</span> 123 Tech Lane, Silicon Valley, CA 94000</p>
                                            </div>
                                        </div>

                                        <div className="row mt-4">
                                            <div className="col-12">
                                                <h6 className="d-flex justify-content-between align-items-center">
                                                    Allocated Assets
                                                    <button className="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#allocateAssetModal">
                                                        <i className="bi bi-plus me-1"></i>Allocate Asset
                                                    </button>
                                                </h6>
                                                <div className="table-responsive">
                                                    <table className="table table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th>Asset Name</th>
                                                                <th>Asset Id</th>
                                                                <th>Allocation Date</th>
                                                                <th>Actions</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr>
                                                                <td>MacBook Pro 16"</td>
                                                                <td>Asset #1234</td>
                                                                <td>February 1, 2023</td>
                                                                <td>
                                                                    <div className="btn-group" role="group">
                                                                        <button className="btn btn-sm btn-info" title="View Asset" data-bs-toggle="modal" data-bs-target="#assetDetailsModal">
                                                                            <i className="bi bi-eye"></i>
                                                                        </button>
                                                                        <button className="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#assetReturnModal" title="Return Asset">
                                                                            <i className="bi bi-arrow-return-left"></i>
                                                                        </button>
                                                                        <button className="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#deallocateAssetModal" title="Deallocate">
                                                                            <i className="bi bi-x-circle"></i>
                                                                        </button>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>Dell Monitor 27"</td>
                                                                <td>Asset #5678</td>
                                                                <td>February 1, 2023</td>
                                                                <td>
                                                                    <div className="btn-group" role="group">
                                                                        <button className="btn btn-sm btn-info" title="View Asset" data-bs-toggle="modal" data-bs-target="#assetDetailsModal">
                                                                            <i className="bi bi-eye"></i>
                                                                        </button>
                                                                        <button className="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#assetReturnModal" title="Return Asset">
                                                                            <i className="bi bi-arrow-return-left"></i>
                                                                        </button>
                                                                        <button className="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#deallocateAssetModal" title="Deallocate">
                                                                            <i className="bi bi-x-circle"></i>
                                                                        </button>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {/* <!-- Add Employee Modal --> */}
                        <div className="modal fade" id="addEmployeeModal" tabIndex="-1">
                            <div className="modal-dialog modal-lg">
                                <div className="modal-content">
                                    <div className="modal-header bg-success text-white">
                                        <h5 className="modal-title">Add New Employee</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <form>
                                            <div className="row">
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Full Name</label>
                                                    <input type="text" className="form-control"
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "name": e.target.value }) }} required />
                                                </div>
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Username</label>
                                                    <input type="text" className="form-control"
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "user": { "username": e.target.value } }) }} required />
                                                </div>
                                            </div>
                                            <div className="row">
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Email</label>
                                                    <input type="email" className="form-control"
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "email": e.target.value }) }} required />
                                                </div>
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Phone</label>
                                                    <input type="tel" className="form-control"
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "contact": e.target.value }) }} />
                                                </div>
                                            </div>
                                            <div className="row">
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Password</label>
                                                    <input type="password" className="form-control"
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "user": { ...selectedEmployee.user, "password": e.target.value } }) }} />
                                                </div>
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Department</label>
                                                    <select className="form-select"
                                                        onChange={(e) => {
                                                            setDepartmentId(e.target.value)
                                                        }}>
                                                        <option value="">-- Select Department --</option>
                                                        {
                                                            department.map((d, index) => (
                                                                <option key={index} value={d.id}>{d.name}</option>
                                                            ))
                                                        }
                                                    </select>
                                                </div>
                                            </div>
                                            <div className="mb-3">
                                                <label className="form-label">Address</label>
                                                <textarea className="form-control" rows="3"
                                                    onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "address": e.target.value }) }}></textarea>
                                            </div>
                                        </form>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="button" className="btn btn-success" onClick={() => { addEmployee(selectedEmployee, departmentId) }} data-bs-dismiss="modal">Add Employee</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {/* <!-- Edit Employee Modal --> */}
                        {selectedEmployee && <div className="modal fade" id="editEmployeeModal" tabIndex="-1">
                            <div className="modal-dialog modal-lg">
                                <div className="modal-content">
                                    <div className="modal-header bg-warning">
                                        <h5 className="modal-title">Edit Employee</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <form>
                                            <div className="row">
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Full Name</label>
                                                    <input type="text" className="form-control" value={selectedEmployee.name}
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "name": e.target.value }) }} required />
                                                </div>
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Email</label>
                                                    <input type="email" className="form-control" value={selectedEmployee.email}
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "name": e.target.value }) }} required />
                                                </div>
                                            </div>
                                            <div className="row">
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Phone</label>
                                                    <input type="tel" className="form-control" value={selectedEmployee.contact}
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "name": e.target.value }) }} />
                                                </div>
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Department</label>
                                                    <select className="form-select" value={selectedEmployee.department?.id}
                                                        onChange={(e) => {
                                                            setDepartmentId(e.target.value)
                                                        }}>
                                                        {
                                                            department.map((d, index) => (
                                                                <option key={index} value={d.id}>{d.name}</option>
                                                            ))
                                                        }
                                                    </select>
                                                </div>
                                            </div>
                                            <div className="mb-3">
                                                <label className="form-label">Address</label>
                                                <textarea className="form-control" rows="3"
                                                    value={selectedEmployee.address}
                                                    onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "name": e.target.value }) }}></textarea>
                                            </div>
                                        </form>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="button" className="btn btn-warning"
                                            onClick={() => { editEmployee(selectedEmployee) }} data-bs-dismiss="modal">Save Changes</button>
                                    </div>
                                </div>
                            </div>
                        </div>}
                        {/* <!-- Delete Confirmation Modal --> */}
                        <div className="modal fade" id="deleteConfirmModal" tabIndex="-1">
                            <div className="modal-dialog">
                                <div className="modal-content">
                                    <div className="modal-header bg-danger text-white">
                                        <h5 className="modal-title">Confirm Employee Deletion</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <div className="alert alert-danger">
                                            <strong>Warning:</strong> You are about to permanently delete the employee record for John Doe.
                                        </div>
                                        <p>This action cannot be undone. All associated data will be removed from the system.</p>
                                        <div className="form-check">
                                            <input className="form-check-input" type="checkbox" id="confirmDelete" />
                                            <label className="form-check-label" htmlFor="confirmDelete">
                                                I understand this action is irreversible
                                            </label>
                                        </div>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="button" className="btn btn-danger" disabled id="deleteEmployeeBtn">Delete Employee</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {/* <!-- Allocate Asset Modal --> */}
                        <div className="modal fade" id="allocateAssetModal" tabIndex="-1">
                            <div className="modal-dialog">
                                <div className="modal-content">
                                    <div className="modal-header bg-success text-white">
                                        <h5 className="modal-title">Allocate New Asset</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <form>
                                            <div className="mb-3">
                                                <label className="form-label">Asset</label>
                                                <input type="number" className="form-control" />
                                            </div>
                                            <div className="mb-3">
                                                <label className="form-label">Allocation Date</label>
                                                <input type="date" className="form-control" />
                                            </div>
                                        </form>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="button" className="btn btn-success">Allocate Asset</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {/* <!-- Deallocate Asset Modal --> */}
                        <div className="modal fade" id="deallocateAssetModal" tabIndex="-1">
                            <div className="modal-dialog">
                                <div className="modal-content">
                                    <div className="modal-header bg-warning">
                                        <h5 className="modal-title">Confirm Asset Deallocation</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <div className="alert alert-warning">
                                            <strong>Warning:</strong> You are about to deallocate an asset from this employee.
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label">Asset Details</label>
                                            <input type="text" className="form-control" value="MacBook Pro 16" readOnly />
                                        </div>
                                        <div className="form-check">
                                            <input className="form-check-input" type="checkbox" id="confirmDeallocation" />
                                            <label className="form-check-label" htmlFor="confirmDeallocation">
                                                I confirm this asset deallocation
                                            </label>
                                        </div>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="button" className="btn btn-warning" disabled id="deallocateAssetBtn">Deallocate Asset</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default EmployeeList;