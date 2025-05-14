import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router";

function AddUser() {
    const [name, setName] = useState("")
    const [email, setEmail] = useState("")
    const [phone, setPhone] = useState("")
    const [company, setCompany] = useState("")
    const navigate = useNavigate()
    //function to add user
    const add = async () => {
        try {
            let body = {
                "name": name,
                "email": email,
                "phone": phone,
                "company": company
            }
            await axios.post("https://jsonplaceholder.typicode.com/users", body)
            console.log(body)
        } catch (error) {
            console.log(error)
        }
    }
    return (
        <div>
            <div className="container-fluid" >
                <div className="card">
                    <div className="card-header" style={{ backgroundColor: "bisque" }}>
                        <h3>User Management System</h3>
                    </div>
                    <div className="card-body" style={{ backgroundColor: "azure" }}>
                        <div className="row">
                            <div className="col-sm-4"></div>
                            <div className="col-sm-4">
                                <div className="card">
                                    <div className="card-header">
                                        <h6>Add User</h6>
                                    </div>
                                    <div className="card-body">
                                        <form onSubmit={(e) => { e.preventDefault(); add(); navigate("/users") }} >
                                            <div className="mb-3">
                                                <label className="form-label">Name</label>
                                                <input type="text" className="form-control" placeholder="Enter Name"
                                                    onChange={(e) => { setName(e.target.value) }} />
                                            </div>
                                            <div className="mb-3">
                                                <label className="form-label">Email</label>
                                                <input type="email" className="form-control" placeholder="Enter Email"
                                                    onChange={(e) => { setEmail(e.target.value) }} />
                                            </div>
                                            <div className="mb-3">
                                                <label className="form-label">Phone</label>
                                                <input type="tel" className="form-control" placeholder="Enter Contact Number"
                                                    onChange={(e) => { setPhone(e.target.value) }} />
                                            </div>
                                            <div className="mb-3">
                                                <label className="form-label">Company Name</label>
                                                <input type="text" className="form-control" placeholder="Enter Company Name"
                                                    onChange={(e) => { setCompany(e.target.value) }} />
                                            </div>
                                            <div className="mb-2 text-end">
                                                <button className="btn btn-primary" type="Submit">Add User</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div className="col-sm-4"></div>
                        </div>
                    </div>
                </div>
            </div >
        </div >
    )
}
export default AddUser;