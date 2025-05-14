import { useState, useEffect } from 'react';
import './css/AssetList.css'
import axios from 'axios';
import Sidebar from './SidebarAdmin';

function AssetList() {
    const [assets, setAssets] = useState([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(3);
    const [totalpage, setTotalpage] = useState(0);
    const [pageArray, setPageArray] = useState([]);
    const [selectedAsset, setSelectedAsset] = useState({});
    const [category, setCategory] = useState([]);
    const [categoryId, setCategoryId] = useState();
    const [disable, setDisable] = useState(true);
    const [employee, setEmployee] = useState({});
    const [employeeId, setEmployeeId] = useState("");
    const [allocationDate, setAllocationDate] = useState("");
    const [showAlert, setShowAlert] = useState(false);
    const [alertMsg, setAlertMsg] = useState(null);
    const [empErrorMsg, setEmpErrorMsg] = useState(null);
    const [showErrorAlert, setShowErrorAlert] = useState(false);
    const [errorMsg, setErrorMsg] = useState("")
    const [filterAssetValue, setfilterAssetValue] = useState("")
    const [filterAsset, setfilterAsset] = useState("")


    //to get all the records
    const getAsset = async () => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        //call the api
        const response = await axios
            .get(`http://localhost:8081/api/asset/getall?page=${page}&size=${size}`, headers);
        setAssets(response.data.list);
        setTotalpage(response.data.totalPages);
        let tp = response.data.totalPages;
        setPageArray([]);
        let temp = [];
        while (tp > 0) {
            temp.push(1);
            tp = tp - 1;
        }
        setPageArray(temp);
    }
    //to get all the category
    const getCategory = async () => {
        //call the api
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        try {
            const response = await axios.get("http://localhost:8081/api/category/getall", headers);
            setCategory(response.data);
        }
        catch (err) {
            console.log(err);
        }
    }
    useEffect(() => {
        getAsset();
        getCategory();

    }, [page])

    //function to update the object
    const updateAsset = async (asset) => {

        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        try {
            //call the api to update
            const response = await axios.put(`http://localhost:8081/api/asset/update-asset/${asset.id}`, asset, headers)
            //delete the existing in asset
            let temp = [...assets]
            temp = temp.filter(a => a.id !== asset.id);
            //update the new record
            temp.push(asset);
            setAssets(temp);
            setSelectedAsset({})
            const modalElement = document.getElementById("editAssetModal")
            const modal = bootstrap.Modal.getInstance(modalElement)
            modal.hide()
            setAlertMsg("Asset Updated Successfully")
            alertOpen()
        } catch (error) {
            console.log(error)
        }
    }

    //function to delete the asset
    const deleteAsset = async (asset) => {
        try {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            //call the api to delete
            const response = await axios.delete(`http://localhost:8081/api/asset/delete/${asset.id}`, headers);
            //delete the record in ui
            let temp = [...assets]
            temp = temp.filter(a => a.id !== asset.id);
            setAssets(temp);
            const modalElement = document.getElementById("deleteAssetModal")
            const modal = bootstrap.Modal.getInstance(modalElement)
            modal.hide();
            setAlertMsg("Asset  Deleted Successfully")
            alertOpen()
            setSelectedAsset({})
            setDisable(true)
        }
        catch (err) {
            console.log(err)
        }
    }
    //function to get the employee
    const getEmployeeById = async (empId) => {
        //call the api to get employee
        if (empId === "") setEmployee({});
        if (empId !== "") {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const response = await axios.get(`http://localhost:8081/api/employee/getbyid/${empId}`, headers);
                setEmployee(response.data);
            }
            catch (err) {
                setEmpErrorMsg("Employee Id is invalid")
            }
        }
    }
    //function to allocate asset
    const allocateAsset = async (asset, emp, date) => {
        try {
            //call the api
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            const response = await axios
                .post(`http://localhost:8081/api/assetallocation/add/${asset.id}/${emp.id}`,
                    { "allocationDate": date }, headers
                )
            // console.log(response)
            //delete the asset and update
            let temp = [...assets]
            temp = temp.filter(a => a.id !== asset.id)
            //add the new
            temp.push(response.data.asset)
            setAssets(temp)
            const modalElement = document.getElementById("allocateAssetModal")
            const modal = bootstrap.Modal.getInstance(modalElement)
            modal.hide()
            setAlertMsg("Asset Allocated Successfully")
            alertOpen()
            setSelectedAsset({})
            setEmployee({})
            setEmployeeId(null)
            setAllocationDate("")
        }
        catch (err) {
            setErrorMsg("Error in Asset Allocation ")
            errorAlertOpen()
            console.log(err);
        }
    }
    //function to add the asset
    const addAsset = async (asset, catId) => {
        try {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            const response = await axios
                .post(`http://localhost:8081/api/asset/add/${catId}`, asset, headers)
            //add the asset in the list
            let temp = [...assets]
            temp.push(response.data)
            setAssets(temp);
            // // Hide the modal manually
            const modalElement = document.getElementById('addAssetModal');
            const modal = bootstrap.Modal.getInstance(modalElement);
            modal.hide();
            setSelectedAsset({});
            setCategoryId("");
            setAlertMsg("Asset added successfully")
            alertOpen()

        }
        catch (err) {
            console.log(err);
            setErrorMsg("Asset is not added")
            errorAlertOpen()
        }
    }
    //function to filter asset
    const callAssetFilter = async () => {
        if (filterAsset === "asset") {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const response = await axios.get(`http://localhost:8081/api/asset/getbyid/${filterAssetValue}`, headers)
                console.log(response)
                setPageArray([1]);
                setAssets([response.data])
            } catch (error) {
                setErrorMsg("Invalid Id")
                errorAlertOpen()
                console.log(error)
            }
        }
        else if (filterAsset === "category") {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            //call the api
            if (filterAssetValue === "--Select Category--") {
                setErrorMsg("Please select category")
                errorAlertOpen()
                console.log(error)
            }
            try {
                const response = await axios.get(`http://localhost:8081/api/asset/getbycategory?category=${filterAssetValue}&page=${page}&size=${size}`, headers);
                setAssets(response.data.list);
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
        else if (filterAsset === "all") {
            getAsset()
        }
    }
    //function to show error message
    const errorAlertOpen = () => { setShowErrorAlert(true) }
    const errorAlertClose = () => { setShowErrorAlert(false); setErrorMsg(null) }
    //function to show alert
    const alertOpen = () => { setShowAlert(true) }
    const alertClose = () => { setShowAlert(false); setAlertMsg(null) }
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
                            <div className="card-header d-flex justify-content-between align-items-center" style={{ backgroundColor: "#159895", color: "white" }}>
                                <span><h5>Company Assets</h5></span>
                                <div>
                                    <div className='card text-end' style={{ backgroundColor: "#159895" }}>
                                        <form onSubmit={(e) => { e.preventDefault(), callAssetFilter() }}>
                                            <div className='row justify-content-end align-items-center g-2'>
                                                <div className='col-auto'>
                                                    <select className="form-select " defaultValue="all" onChange={(e) => { setfilterAsset(e.target.value) }}>
                                                        <option value="all" >All</option>
                                                        <option value="asset">Asset Id</option>
                                                        <option value="category" >Category</option>
                                                    </select>
                                                </div>
                                                {filterAsset !== "all" && <div className='col-auto'>
                                                    {
                                                        filterAsset === "asset" ? <input className='form-control' placeholder='Enter Asset Id' defaultValue=""
                                                            onChange={(e) => {
                                                                setfilterAssetValue(e.target.value); if (e.target.value == "") {
                                                                    getAsset();
                                                                }
                                                            }} /> :
                                                            filterAsset === "category" ?
                                                                <select className="form-select" defaultValue="--Select Category--" onChange={(e) => { setfilterAssetValue(e.target.value) }} >
                                                                    <option value="--Select Category--">--Select Category--</option>
                                                                    {category.map((d, index) => (
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
                                            <button className="btn btn-light btn-sm" data-bs-toggle="modal" data-bs-target="#addAssetModal"
                                                onClick={() => { setSelectedAsset({}) }}>
                                                <i className="bi bi-plus me-2"></i>Add Asset
                                            </button>
                                        </div>
                                    </div>
                                </div></div>
                            <div className="card-body">
                                <div className="row">
                                    {
                                        assets.sort((a, b) => a.id - b.id).map((a, index) => (
                                            <div className="col-md-4 mb-3" key={index}>
                                                <div className="card hover-effect">
                                                    <div className="card-body">
                                                        <div className="d-flex justify-content-between mb-3">
                                                            <h5 className="card-title">{a.name}</h5>
                                                            <span className="badge bg-secondary">Quantity: {a.quantity}</span>
                                                        </div>
                                                        <p className="card-text">
                                                            <i className="bi bi-tag me-2"></i>{a.id}<br />
                                                            <i className="bi bi-briefcase me-2"></i>{a.category.name}
                                                        </p>
                                                        <div className="d-flex justify-content-between">
                                                            <button className="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#viewAssetModal"
                                                                onClick={() => { setSelectedAsset(a) }}>
                                                                <i className="bi bi-eye"></i>
                                                            </button>
                                                            <button className="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#allocateAssetModal"
                                                                onClick={() => { setSelectedAsset(a) }}>
                                                                <i className="bi bi-person-plus"></i>
                                                            </button>
                                                            <button className="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#editAssetModal"
                                                                onClick={() => { setSelectedAsset(a) }}>
                                                                <i className="bi bi-pencil"></i>
                                                            </button>
                                                            <button className="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#deleteAssetModal"
                                                                onClick={() => { setSelectedAsset(a) }}>
                                                                <i className="bi bi-trash"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        ))
                                    }
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
                    </div>
                </div>
            </div>
            {/* <!-- New Allocate Asset Modal --> */}
            {selectedAsset && <div className="modal fade" id="allocateAssetModal" tabIndex="-1">
                <div className="modal-dialog modal-lg">
                    <div className="modal-content">
                        <div className="modal-header bg-success text-white">
                            <h5 className="modal-title">Allocate Asset</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div className="modal-body">
                            <form onSubmit={(e) => { e.preventDefault(), allocateAsset(selectedAsset, employee, allocationDate) }}>
                                <div className="row">
                                    <div className="col-md-6 mb-3">
                                        <label className="form-label">Asset ID</label>
                                        <input type="text" className="form-control" value={selectedAsset.id || ""} readOnly />
                                    </div>
                                    <div className="col-md-6 mb-3">
                                        <label className="form-label">Asset Name</label>
                                        <input type="text" className="form-control" value={selectedAsset.name || ""} readOnly />
                                    </div>
                                </div>
                                <div className="row mb-3">
                                    <div className="col-md-6">
                                        <label className="form-label">Employee ID</label><span style={{ color: "red" }}>*</span>
                                        {empErrorMsg != null ? <p style={{ color: "red" }}> {empErrorMsg} </p> : ""}
                                        <div className='d-flex'>
                                            <input type="text" className="form-control me-2" placeholder="Enter Employee ID"
                                                value={employeeId || ""}
                                                onChange={(e) => {
                                                    setEmpErrorMsg(null)
                                                    const evl = e.target.value
                                                    if (evl === "") {
                                                        setEmployee({})
                                                        setEmployeeId("")
                                                    }
                                                    else { setEmployeeId(e.target.value) }
                                                }} required />

                                            <button type="button" className="form-control btn btn-sm btn-primary"
                                                onClick={() => { getEmployeeById(employeeId) }}
                                            > Get   </button>
                                        </div>
                                    </div>
                                    <div className="col-md-6 mb-3">
                                        <label className="form-label">Employee Name</label>
                                        <input type="text" className="form-control" value={employee.name || ""} readOnly />
                                    </div>
                                </div>
                                <div className="row">

                                    <div className="col-md-6 mb-3">
                                        <label className="form-label">Allocation Date</label><span style={{ color: "red" }}>*</span>
                                        <input type="date" className="form-control" value={allocationDate || ""}
                                            onChange={(e) => { setAllocationDate(e.target.value) }} required />
                                    </div>
                                </div>

                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                    <button type="submit" className="btn btn-success" >Allocate Asset</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>}
            {/* <!-- View Asset Modal --> */}
            {selectedAsset && <div className="modal fade" id="viewAssetModal" tabIndex="-1">
                <div className="modal-dialog modal-lg">
                    <div className="modal-content">
                        <div className="modal-header bg-info text-white">
                            <h5 className="modal-title">Asset Details</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div className="modal-body">
                            <div className="row">
                                <div className="col-md-6">
                                    <p><span className="detail-label">Asset Name:</span> {selectedAsset.name}</p>
                                    <p><span className="detail-label">Asset ID:</span> {selectedAsset.id}</p>
                                    <p><span className="detail-label">Model:</span> {selectedAsset.model}</p>
                                    <p><span className="detail-label">Type:</span> {selectedAsset.category?.name || ""}</p>
                                    <p><span className="detail-label">Status:</span> <span className="badge bg-success">Available</span></p>
                                </div>
                                <div className="col-md-6">
                                    <p><span className="detail-label">Quantity:</span> {selectedAsset.quantity}</p>
                                    <p><span className="detail-label">Purchase Date:</span> {selectedAsset.date}</p>
                                </div>
                            </div>
                            <div className="row mt-3">
                                <div className="col-12">
                                    <h6 className="detail-label">Configurations:</h6>
                                    <div className="p-3 border rounded bg-light">
                                        <p className="mb-0">
                                            {selectedAsset.configuration}
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
            {/* <!-- Edit Asset Modal --> */}
            {selectedAsset && <div className="modal fade" id="editAssetModal" tabIndex="-1">
                <div className="modal-dialog modal-lg">
                    <div className="modal-content">
                        <div className="modal-header bg-warning">
                            <h5 className="modal-title">Edit Asset</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div className="modal-body">
                            <form onSubmit={(e) => { e.preventDefault(), updateAsset(selectedAsset) }}>
                                <div className="row">
                                    <div className="col-md-6 mb-3">
                                        <label className="form-label">Asset Name</label><span style={{ color: "red" }}>*</span>
                                        <input type="text" className="form-control" value={selectedAsset.name || ""}
                                            onChange={(e) => setSelectedAsset({ ...selectedAsset, "name": e.target.value })} required />
                                    </div>
                                    <div className="col-md-6 mb-3">
                                        <label className="form-label">Asset ID</label>
                                        <input type="text" className="form-control" value={selectedAsset.id || ""} readOnly />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6 mb-3">
                                        <label className="form-label">Model</label><span style={{ color: "red" }}>*</span>
                                        <input type="text" className="form-control" value={selectedAsset.model || ""}
                                            onChange={(e) => setSelectedAsset({ ...selectedAsset, "model": e.target.value })} required />
                                    </div>
                                    <div className="col-md-6 mb-3">
                                        <label className="form-label">Asset Type</label><span style={{ color: "red" }}>*</span>
                                        <select value={selectedAsset.category?.name || ""} className="form-select" onChange={(e) => {
                                            let found = e.target.value
                                            let temp = [...category];
                                            let cat = temp.find(c => c.name === found)
                                            setSelectedAsset({
                                                ...selectedAsset, "category": {
                                                    "name": cat.name,
                                                    "id": cat.id
                                                }
                                            })
                                        }} >
                                            {
                                                category.map((c, index) => (
                                                    <option value={c.name} key={index} >{c.name}</option>
                                                ))
                                            }
                                        </select>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6 mb-3">
                                        <label className="form-label">Purchase Date</label>
                                        <input type="date" className="form-control" value={selectedAsset.date || ""}
                                            onChange={(e) => setSelectedAsset({ ...selectedAsset, "date": e.target.value })} />
                                    </div>
                                    <div className="col-md-6 mb-3">
                                        <label className="form-label">Quantity</label>
                                        <input type="number" className="form-control" value={selectedAsset.quantity || ""} min="1"
                                            onChange={(e) => setSelectedAsset({ ...selectedAsset, "quantity": e.target.value })} />
                                    </div>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">Configurations</label>
                                    <textarea className="form-control" rows="4" value={selectedAsset.configuration || ""}
                                        onChange={(e) => setSelectedAsset({ ...selectedAsset, "configuration": e.target.value })} ></textarea>
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
            {/* <!-- Delete Asset Modal --> */}
            {selectedAsset && <div className="modal fade" id="deleteAssetModal" tabIndex="-1">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-danger text-white">
                            <h5 className="modal-title">Confirm Asset Deletion</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div className="modal-body">
                            <div className="alert alert-danger">
                                <strong>Warning:</strong> You are about to permanently delete the asset {selectedAsset.name}.
                            </div>
                            <p>This action cannot be undone. The asset will be removed from the system.</p>
                            <div className="form-check">
                                <input className="form-check-input" type="checkbox" id="confirmDelete"
                                    onClick={() => { disable ? setDisable(false) : setDisable(true) }} />
                                <label className="form-check-label" htmlFor="confirmDelete">
                                    I understand this action is irreversible
                                </label>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            {disable === false ? <button type="button" className="btn btn-danger" id="deleteAssetBtn"
                                onClick={() => { deleteAsset(selectedAsset) }} data-bs-dismiss="modal">Delete Asset</button> :
                                <button type="button" className="btn btn-danger" disabled id="deleteAssetBtn">Delete Asset</button>}
                        </div>
                    </div>
                </div>
            </div>}
            {/* <!-- Add Asset Modal --> */}
            <div className="modal fade" id="addAssetModal" tabIndex="-1">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-primary text-white">
                            <h5 className="modal-title">Add New Asset</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div className="modal-body">
                            <form onSubmit={(e) => { e.preventDefault(), addAsset(selectedAsset, categoryId) }}>
                                <div className="mb-3">
                                    <label className="form-label">Asset Name</label><span style={{ color: "red" }}>*</span>
                                    <input type="text" className="form-control" value={selectedAsset.name || ""}
                                        onChange={(e) => setSelectedAsset({ ...selectedAsset, "name": e.target.value })} required />
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">Asset Model</label><span style={{ color: "red" }}>*</span>
                                    <input type="text" className="form-control" value={selectedAsset.model || ""}
                                        onChange={(e) => setSelectedAsset({ ...selectedAsset, "model": e.target.value })} required />
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">Purchase Date</label><span style={{ color: "red" }}>*</span>
                                    <input type="date" className="form-control" value={selectedAsset.date || ""}
                                        onChange={(e) => setSelectedAsset({ ...selectedAsset, "date": e.target.value })} required />
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">Quantity</label><span style={{ color: "red" }}>*</span>
                                    <input type="number" min="1" className="form-control" value={selectedAsset.quantity || ""}
                                        onChange={(e) => setSelectedAsset({ ...selectedAsset, "quantity": e.target.value })} required />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <label className="form-label">Asset Type</label><span style={{ color: "red" }}>*</span>
                                    <select className="form-select"
                                        onChange={(e) => { setCategoryId(e.target.value) }}>
                                        <option value="">-- Select Category --</option>
                                        {
                                            category.map((c, index) => (
                                                <option value={c.id} key={index} >{c.name}</option>
                                            ))
                                        }
                                    </select>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">Configurations</label><span style={{ color: "red" }}>*</span>
                                    <textarea className="form-control" rows="4" value={selectedAsset.configuration || ""}
                                        onChange={(e) => setSelectedAsset({ ...selectedAsset, "configuration": e.target.value })} required></textarea>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">Description</label>
                                    <textarea className="form-control" rows="4" value={selectedAsset.description || ""}
                                        onChange={(e) => setSelectedAsset({ ...selectedAsset, "description": e.target.value })} ></textarea>
                                </div>

                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                    <button type="submit" className="btn btn-primary">Add Asset</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div >

        </div >
    )
}
export default AssetList;