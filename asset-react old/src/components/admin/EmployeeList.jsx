import { useEffect, useState } from 'react';
import './css/EmployeeList.css'
import Sidebar from './SidebarAdmin';
import axios from 'axios';

function EmployeeList() {
    const [employees, setEmployees] = useState([]);
    const [page, setPage] = useState(0);
    const [size] = useState(9);
    const [pageArray, setPageArray] = useState([]);
    const [totalpage, setTotalpage] = useState(0);
    const [department, setDepartment] = useState([]);
    const [departmentId, setDepartmentId] = useState();
    const [disable, setDisable] = useState(true);
    const [disableDeallocate, setDisableDeallocate] = useState(true);
    const [selectedEmployee, setSelectedEmployee] = useState({});
    const [allocation, setAllocation] = useState([]);
    const [singleAllocation, setSingleAllocation] = useState("");
    const [asset, setAsset] = useState({});
    const [assetId, setAssetId] = useState("");
    const [allocationDate, setAllocationDate] = useState([]);
    const [showAlert, setShowAlert] = useState(false);
    const [showErrorAlert, setShowErrorAlert] = useState(false);
    const [alertMsg, setAlertMsg] = useState("");
    const [errorMsg, setErrorMsg] = useState("")
    const [assetIdError, setassetIdError] = useState("")
    const [filterEmployee, setfilterEmployee] = useState("")
    const [filterEmployeeValue, setfilterEmployeeValue] = useState("")

    const getEmployees = async () => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        //call the api
        try {
            const response = await axios.get(`http://localhost:8081/api/employee/getall?page=${page}&size=${size}`, headers);
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
    //useeffect
    useEffect(() => {
        const getDepartment = async () => {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const response = await axios.get("http://localhost:8081/api/department/getall", headers);
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
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        try {
            const response = await axios
                .post(`http://localhost:8081/api/employee/add-employee/${departId}`, employee, headers)
            console.log(employee);
            //add it to the list
            let temp = [...employees];
            temp.push(response.data);
            setEmployees(temp);
            const modalElement = document.getElementById("addEmployeeModal")
            const modal = bootstrap.Modal.getInstance(modalElement)
            modal.hide()
            setSelectedEmployee({})
            setAlertMsg("Employee added successfully");
            alertOpen()

        } catch (error) {
            setErrorMsg("Contact name should contain 10 digits")
            errorAlertOpen()
            console.log(error);
        }
    }
    //edit the employee
    const editEmployee = async (employee) => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        try {
            console.log(employee)
            const response = await axios.put(`http://localhost:8081/api/employee/update/${employee.id}`, {
                "name": employee.name,
                "email": employee.email,
                "contact": employee.contact,
                "address": employee.address,
                "department": {
                    "id": employee.department.id
                }
            }, headers)
            //update in ui
            //delete the updated and add this
            let temp = [...employees]
            temp = temp.filter(e => e.id !== employee.id)
            temp.push(response.data)
            setEmployees(temp)
            const modalElement = document.getElementById("editEmployeeModal")
            const modal = bootstrap.Modal.getInstance(modalElement)
            modal.hide()
            setSelectedEmployee({})
            setAlertMsg("Employee updated successfully");
            alertOpen()
        } catch (error) {
            setErrorMsg("Contact Should contain 10 digits")
            errorAlertOpen()
            console.log(error)
        }
    }
    //delete the employee
    const deleteEmployee = async (empId) => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        try {
            const response = await axios.delete(`http://localhost:8081/api/employee/delete/${empId}`, headers)
            let temp = [...employees]
            temp = temp.filter(a => a.id !== empId)
            setEmployees(temp)
            setAlertMsg("Employee deleted successfully");
            alertOpen()

        } catch (error) {
            setErrorMsg("Error in deletion")
            errorAlertOpen()
            console.log(error)
        }
    }
    //get the allocation for particular employee
    const getAllocation = async (empId) => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        try {
            const response = await axios.get(`http://localhost:8081/api/assetallocation/byEmpId?empId=${empId}`, headers)
            setAllocation(response.data);
        } catch (error) {
            console.log(error)
        }
    }

    //function to get the employee
    const getAssetById = async (assetId) => {
        //call the api to get employee
        if (assetId === "") setAsset({});
        if (assetId !== "") {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const response = await axios.get(`http://localhost:8081/api/asset/getbyid/${assetId}`, headers);
                setAsset(response.data);
            }
            catch (err) {
                setassetIdError("Asset Id is invalid")
                console.log(err);
            }
        }
    }
    //function to allocate asset
    const allocateAsset = async (employee, asset, date) => {
        try {
            //call the api
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            const response = await axios
                .post(`http://localhost:8081/api/assetallocation/add/${asset.id}/${employee.id}`,
                    { "allocationDate": date }, headers
                )
            // console.log(response)
            let temp = [...allocation]
            temp.push(response.data)
            setAllocation(temp)
            const modalElement = document.getElementById("allocateAssetModal2")
            const modal = bootstrap.Modal.getInstance(modalElement)
            modal.hide()
            setAssetId("")
            setAsset("")
            setAlertMsg("Asset Allocated successfully");
            alertOpen()
        }
        catch (err) {
            setErrorMsg("Error in allocation")
            errorAlertOpen()
            console.log(err);
        }
    }

    //function to deallocate
    const deallocate = async (allocation1) => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        let body = {
            "allocationDate": allocation1.allocationDate,
            "status": "RETURNED",
            "asset": allocation1.asset,
            "reason": allocation1.reason,
            "employee": {
                "department": allocation1.employee.department,
                "name": allocation1.employee.name,
                "email": allocation1.employee.email,
                "contact": allocation1.employee.contact,
                "address": allocation1.employee.address,
                "id": allocation1.employee.id
            }
        }
        try {
            console.log(allocation1)
            const response = await axios.put(`http://localhost:8081/api/assetallocation/update/${allocation1.id}`
                , body, headers)
            //delete it from the allocation
            console.log(response)
            let temp = [...allocation]
            temp = temp.filter(a => a.id !== allocation1.id)
            setAllocation(temp)
            setAlertMsg("Asset Deallocated successfully");
            alertOpen()
        } catch (error) {
            setErrorMsg("Error in deallocation")
            errorAlertOpen()
            console.log(error)
        }

    }
    //function to filter the employee
    const callEmployeeFilter = async () => {
        if (filterEmployee === "employee") {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const response = await axios.get(`http://localhost:8081/api/employee/getbyid/${filterEmployeeValue}`, headers)
                console.log(response)
                setPageArray([1]);
                setEmployees([response.data])
                console.log(employees)
            } catch (error) {
                setErrorMsg("Invalid Id")
                errorAlertOpen()
                console.log(error)
            }
        }
        else if (filterEmployee === "Department") {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            //call the api
            if (filterEmployeeValue === "--Select Department--") {
                setErrorMsg("Please select department")
                errorAlertOpen()
                console.log(error)
            }
            try {
                const response = await axios.get(`http://localhost:8081/api/employee/getbydepartment?department=${filterEmployeeValue}&page=${page}&size=${size}`, headers);
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
        else if (filterEmployee === "all") {
            getEmployees()
        }
    }
    //function to show alert
    const alertOpen = () => { setShowAlert(true) }
    const alertClose = () => { setShowAlert(false); setAlertMsg(null) }
    //function to shw error alert
    const errorAlertOpen = () => { setShowErrorAlert(true) }
    const errorAlertClose = () => { setShowErrorAlert(false); setErrorMsg(null) }
    return (
        <div>
            <div className="container-fluid">
                <div className="row">
                    {/* <!-- Sidebar Component --> */}
                    <Sidebar />
                    {/* <!-- Main Content Area --> */}
                    {showAlert && (
                        <div
                            className="position-fixed top-0 start-50 translate-middle-x p-3"
                            style={{ zIndex: 9999 }} >
                            <div className='row'>
                                <div className='col-5'></div>
                                <div className='col-3'>
                                    <div className="alert text-white bg-success alert-dismissible fade show align-items-center border-0" role="alert">
                                        {alertMsg}
                                        <button
                                            type="button"
                                            className="btn-close btn-close-white me-2 m-auto"
                                            aria-label="Close"
                                            onClick={alertClose}
                                        ></button>
                                    </div>
                                </div>
                                <div className='col-4'></div>
                            </div>
                        </div>
                    )}
                    {showErrorAlert && (
                        <div
                            className="position-fixed top-0 start-50 translate-middle-x p-3"
                            style={{ zIndex: 9999 }} >
                            <div className='row'>
                                <div className='col-5'></div>
                                <div className='col-3'>
                                    <div className="alert text-white bg-danger alert-dismissible fade show align-items-center border-0" role="alert">
                                        {errorMsg}
                                        <button
                                            type="button"
                                            className="btn-close btn-close-white me-2 m-auto"
                                            aria-label="Close"
                                            onClick={errorAlertClose}
                                        ></button>
                                    </div>
                                </div>
                                <div className='col-4'></div>
                            </div>
                        </div>
                    )}
                    <div className="col-md-10 p-4">
                        <div className="card">
                            <div className="card-header d-flex justify-content-between align-items-center">
                                <span><h5>Employee List</h5></span>
                                <div>
                                    <div className='card text-end' style={{ backgroundColor: "#159895" }}>
                                        <form onSubmit={(e) => { e.preventDefault(), callEmployeeFilter() }}>
                                            <div className='row justify-content-end align-items-center g-2'>
                                                <div className='col-auto'>
                                                    <select className="form-select " defaultValue="all" onChange={(e) => { setfilterEmployee(e.target.value) }}>
                                                        <option value="all" >All</option>
                                                        <option value="employee">Employee Id</option>
                                                        <option value="Department" >Department</option>
                                                    </select>
                                                </div>
                                                {filterEmployee !== "all" && <div className='col-auto'>
                                                    {
                                                        filterEmployee === "employee" ? <input className='form-control' placeholder='Enter employee Id' defaultValue=""
                                                            onChange={(e) => {
                                                                setfilterEmployeeValue(e.target.value); if (e.target.value == "") {
                                                                    getEmployees();
                                                                }
                                                            }} /> :
                                                            filterEmployee === "Department" ?
                                                                <select className="form-select" defaultValue="--Select Department--" onChange={(e) => { setfilterEmployeeValue(e.target.value) }} >
                                                                    <option value="--Select Department--">--Select Department--</option>
                                                                    {department.map((d, index) => (
                                                                        <option key={index} value={d.name} >{d.name}</option>
                                                                    ))}
                                                                </select> : ""
                                                    }
                                                </div>}
                                                <div className='col-auto'>
                                                    <button type='submit'
                                                        className='btn btn-primary btn-sm'>Search</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div className='row justify-content-end mt-2'>
                                        <div className='col-auto'>
                                            <button className="btn btn-light btn-sm" data-bs-toggle="modal" data-bs-target="#addEmployeeModal"
                                                onClick={() => {
                                                    setSelectedEmployee({
                                                        "name": "", "email": "", "contact": "",
                                                        "address": "",
                                                        "department": {
                                                            "id": "", "name": ""
                                                        },
                                                        "user": {
                                                            "username": "", "password": ""
                                                        }
                                                    })
                                                }}>
                                                <i className="bi bi-plus me-2"></i>Add Employee
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card-body">
                                <div className="row">
                                    {employees.sort((a, b) => a.id - b.id).map((e, index) => (
                                        <div key={index} className="col-md-4 mb-3">
                                            <div className="card employee-card hover-effect" >
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
                                                        <button className="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#viewEmployeeModal"
                                                            onClick={() => { setSelectedEmployee(e), getAllocation(e.id) }}>
                                                            <i className="bi bi-eye"></i>
                                                        </button>
                                                        <button className="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#editEmployeeModal"
                                                            onClick={() => { setSelectedEmployee(e) }}>
                                                            <i className="bi bi-pencil"></i>
                                                        </button>
                                                        <button className="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal"
                                                            onClick={() => { setSelectedEmployee(e) }}>
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
                                            <ul className="pagination">
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
                                                        <a className="page-link" href="#"
                                                            onClick={() => { setPage(index) }}>{index + 1}</a></li>
                                                ))}
                                                {
                                                    page === totalpage - 1 ? <li className="page-item disabled">
                                                        <a className="page-link" href="#" aria-disabled="true">Next</a>
                                                    </li> : <li className="page-item">
                                                        <a className="page-link" href="#" onClick={() => { setPage(page + 1) }}>Next</a>
                                                    </li>
                                                }
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {/* <!-- New Asset Details Modal --> */}
                        {singleAllocation && <div className="modal fade" id="assetDetailsModal" tabIndex="-1">
                            <div className="modal-dialog modal-lg">
                                <div className="modal-content">
                                    <div className="modal-header">
                                        <h5 className="modal-title">Asset Details</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <div className="row">
                                            <div className="col-md-6">
                                                <h6 className="detail-label">Asset Details</h6>
                                                <p><strong>Asset Name:</strong> {singleAllocation.asset?.name}</p>
                                                <p><strong>Asset Number:</strong>{singleAllocation.asset?.id}</p>
                                                <p><strong>Model:</strong> {singleAllocation.asset?.model}</p>
                                                <p><strong>Purchase Date:</strong> {singleAllocation.asset?.date}</p>
                                            </div>
                                            <div className="col-md-6">
                                                <h6 className="detail-label">Allocation Details</h6>
                                                <p><strong>Allocation Id:</strong> {singleAllocation.id}</p>
                                                <p><strong>Allocation Date:</strong> {singleAllocation.allocationDate}</p>
                                                <p><strong>Quantity:</strong> {singleAllocation.asset?.quantity}</p>
                                            </div>
                                        </div>
                                        <div className="row mt-3">
                                            <div className="col-12">
                                                <h6 className="detail-label">Configurations:</h6>
                                                <div className="p-3 border rounded bg-light">
                                                    <p className="mb-0">
                                                        {singleAllocation.asset?.configuration}
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>}
                        {/* <!-- View Employee Modal --> */}
                        {selectedEmployee && <div className="modal fade" id="viewEmployeeModal" tabIndex="-1">
                            <div className="modal-dialog modal-lg">
                                <div className="modal-content">
                                    <div className="modal-header">
                                        <h5 className="modal-title">Employee Details</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <div className="row">
                                            <div className="col-md-8">
                                                <h4>{selectedEmployee.name}</h4>
                                                <p><span className="detail-label">Id:</span> {selectedEmployee.id}</p>
                                                <p><span className="detail-label">Email:</span> {selectedEmployee.email}</p>
                                                <p><span className="detail-label">Phone:</span> {selectedEmployee.contact}</p>
                                                <p><span className="detail-label">Department:</span> {selectedEmployee.department?.name}</p>
                                                <p><span className="detail-label">Address:</span> {selectedEmployee.address}</p>
                                            </div>
                                        </div>

                                        <div className="row mt-4">
                                            <div className="col-12">
                                                <h6 className="d-flex justify-content-between align-items-center">
                                                    Allocated Assets
                                                    <button className="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#allocateAssetModal2">
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
                                                            {
                                                                allocation.map((e, index) => (
                                                                    <tr key={index}>
                                                                        <td>{e.asset.name}</td>
                                                                        <td>{e.asset.id}</td>
                                                                        <td>{e.allocationDate}</td>
                                                                        <td>
                                                                            <div className="btn-group" role="group">
                                                                                <button className="btn btn-sm btn-info" title="View Asset" data-bs-toggle="modal" data-bs-target="#assetDetailsModal"
                                                                                    onClick={() => { setSingleAllocation(e) }} data-bs-dismiss="modal">
                                                                                    <i className="bi bi-eye"></i>
                                                                                </button>
                                                                                <button className="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#deallocateAssetModal" title="Deallocate"
                                                                                    onClick={() => { setSingleAllocation(e) }}>
                                                                                    <i className="bi bi-x-circle"></i>
                                                                                </button>
                                                                            </div>
                                                                        </td>
                                                                    </tr>

                                                                ))
                                                            }
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
                        </div>}
                        {/* <!-- Add Employee Modal --> */}
                        <div className="modal fade" id="addEmployeeModal" tabIndex="-1">
                            <div className="modal-dialog modal-lg">
                                <div className="modal-content">
                                    <div className="modal-header bg-success text-white">
                                        <h5 className="modal-title">Add New Employee</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <form onSubmit={(e) => { e.preventDefault(); addEmployee(selectedEmployee, departmentId) }}>
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
                                                    <input type="tel" maxLength={10} className="form-control"
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "contact": e.target.value }) }} required />
                                                </div>
                                            </div>
                                            <div className="row">
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Password</label>
                                                    <input type="password" className="form-control"
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "user": { ...selectedEmployee.user, "password": e.target.value } }) }} required />
                                                </div>
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Department</label>
                                                    <select className="form-select"
                                                        onChange={(e) => {
                                                            setDepartmentId(e.target.value)
                                                        }} required>
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

                                            <div className="modal-footer">
                                                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                <button type="submit" className="btn btn-success" >Add Employee</button>
                                            </div>
                                        </form>
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
                                        <form onSubmit={(e) => { e.preventDefault(); editEmployee(selectedEmployee) }}>
                                            <div className="row">
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Full Name</label>
                                                    <input type="text" className="form-control" value={selectedEmployee.name || ""}
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "name": e.target.value }) }} required />
                                                </div>
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Email</label>
                                                    <input type="email" className="form-control" value={selectedEmployee.email || ""}
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "email": e.target.value }) }} required />
                                                </div>
                                            </div>
                                            <div className="row">
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Phone</label>
                                                    <input type="tel" maxLength={10} className="form-control" value={selectedEmployee.contact || ""}
                                                        onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "contact": e.target.value }) }} required />
                                                </div>
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Department</label>
                                                    <select className="form-select" defaultValue={selectedEmployee.department?.id || ""}
                                                        onChange={(e) => {
                                                            setSelectedEmployee({
                                                                ...selectedEmployee,
                                                                "department": {
                                                                    "id": e.target.value
                                                                }
                                                            })
                                                        }} required>
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
                                                    value={selectedEmployee.address || ""}
                                                    onChange={(e) => { setSelectedEmployee({ ...selectedEmployee, "address": e.target.value }) }}></textarea>
                                            </div>

                                            <div className="modal-footer">
                                                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                <button type="submit" className="btn btn-warning">Save Changes</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>}
                        {/* <!-- Delete Confirmation Modal --> */}
                        {selectedEmployee && <div className="modal fade" id="deleteConfirmModal" tabIndex="-1">
                            <div className="modal-dialog">
                                <div className="modal-content">
                                    <div className="modal-header bg-danger text-white">
                                        <h5 className="modal-title">Confirm Employee Deletion</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <div className="alert alert-danger">
                                            <strong>Warning:</strong> You are about to permanently delete the employee record for {selectedEmployee.name}.
                                        </div>
                                        <p>This action cannot be undone. All associated data will be removed from the system.</p>
                                        <div className="form-check">

                                            <input className="form-check-input" type="checkbox"
                                                onChange={() => { disable ? setDisable(false) : setDisable(true) }} id="confirmDelete" />
                                            <label className="form-check-label" htmlFor="confirmDelete">
                                                I understand this action is irreversible
                                            </label>
                                        </div>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        {
                                            disable ? <button type="button" className="btn btn-danger" disabled id="deleteEmployeeBtn">Delete Employee</button>
                                                : <button type="button" className="btn btn-danger" id="deleteEmployeeBtn"
                                                    onClick={() => { deleteEmployee(selectedEmployee.id) }} data-bs-dismiss="modal">Delete Employee</button>
                                        }

                                    </div>
                                </div>
                            </div>
                        </div>}
                        {/* <!-- Deallocate Asset Modal --> */}
                        {singleAllocation && <div className="modal fade" id="deallocateAssetModal" tabIndex="-1">
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
                                            <input type="text" className="form-control" value={singleAllocation.asset?.name} readOnly />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label">Reason</label>
                                            <textarea className='form-control' rows={3} value={singleAllocation.reason || ""} onChange={(e) => {
                                                setSingleAllocation({ ...singleAllocation, "reason": e.target.value })
                                            }}></textarea>
                                        </div>
                                        <div className="form-check">
                                            <input className="form-check-input" type="checkbox" id="confirmDeallocation"
                                                onClick={() => { disableDeallocate ? setDisableDeallocate(false) : setDisableDeallocate(true) }} />
                                            <label className="form-check-label" htmlFor="confirmDeallocation">
                                                I confirm this asset deallocation
                                            </label>
                                        </div>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        {
                                            disableDeallocate ? <button type="button" className="btn btn-warning" disabled id="deallocateAssetBtn">Deallocate Asset</button>
                                                : <button type="button" className="btn btn-warning" id="deallocateAssetBtn"
                                                    onClick={() => { deallocate(singleAllocation) }} data-bs-dismiss="modal">Deallocate Asset</button>
                                        }

                                    </div>
                                </div>
                            </div>
                        </div>}
                        {/* allocate modal */}
                        {selectedEmployee && <div className="modal fade" id="allocateAssetModal2" tabIndex="-1">
                            <div className="modal-dialog modal-lg">
                                <div className="modal-content">
                                    <div className="modal-header bg-success text-white">
                                        <h5 className="modal-title">Allocate Asset</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div className="modal-body">
                                        <form onSubmit={(e) => { e.preventDefault(); allocateAsset(selectedEmployee, asset, allocationDate) }}>
                                            <div className="row">
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Employee ID</label>
                                                    <input type="text" className="form-control" value={selectedEmployee.id || ""} readOnly />
                                                </div>
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Employee Name</label>
                                                    <input type="text" className="form-control" value={selectedEmployee.name || ""} readOnly />
                                                </div>
                                            </div>
                                            <div className="row mb-3">
                                                <div className="col-md-6">
                                                    <label className="form-label">Asset ID</label><span style={{ color: "red" }}>*</span>
                                                    {assetIdError != "" ? <p style={{ color: "red" }}> {assetIdError} </p> : ""}
                                                    <div className='d-flex'>
                                                        <input type="text" className="form-control me-2" placeholder="Enter Asset ID" value={assetId}
                                                            onChange={(e) => {
                                                                setassetIdError("")
                                                                const evl = e.target.value
                                                                if (evl === "") {
                                                                    setAsset({})
                                                                    setAssetId("")
                                                                }
                                                                else { setAssetId(e.target.value) }
                                                            }} required />

                                                        <button type="button" className="form-control btn btn-sm btn-primary"
                                                            onClick={() => { getAssetById(assetId) }}
                                                        > Get   </button>
                                                    </div>
                                                </div>
                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Asset Name</label>
                                                    <input type="text" className="form-control" value={asset.name || ""} readOnly />
                                                </div>
                                            </div>
                                            <div className="row">

                                                <div className="col-md-6 mb-3">
                                                    <label className="form-label">Allocation Date</label><span style={{ color: "red" }}>*</span>
                                                    <input type="date" className="form-control"
                                                        onChange={(e) => { setAllocationDate(e.target.value) }} required />
                                                </div>
                                            </div>
                                            <div className="modal-footer">
                                                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                <button type="submit" className="btn btn-success">Allocate Asset</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>}
                    </div>
                </div>
            </div>
        </div>
    )
}
export default EmployeeList;