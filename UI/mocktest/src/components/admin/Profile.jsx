import { useEffect, useState } from "react";
import Sidebar from "./SidebarAdmin";
import { useSelector } from "react-redux";
import axios from "axios";
import { useNavigate } from "react-router";

function Profile() {
    const profile = useSelector(state => state.profile.profile);
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [contact, setContact] = useState("");
    const [address, setAddress] = useState("");
    const navigate = useNavigate()
    useEffect(() => {
        setName(profile.name)
        setEmail(profile.email)
        setContact(profile.contact)
        setAddress(profile.address)
    }, [])
    //updating the admin
    const update = async (adminId) => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        try {
            const response = await axios.put(`http://localhost:8081/api/admin/update/${adminId}`,
                {
                    "name": name,
                    "email": email,
                    "contact": contact,
                    "address": address
                }, headers
            )
        } catch (error) {
            console.log(error)
        }
    }
    return (
        <div>
            <div className="container-fluid">
                <div className="row">
                    {/* <!-- Sidebar (Original) --> */}
                    <Sidebar />
                    {/* Main Content */}
                    <div className="col-md-10 p-4">
                        <div className="card">
                            <div className="card-header" style={{ backgroundColor: "#159895" }}>
                                <div className="row">
                                    <div className="col-md-10"><h4>Profile</h4></div>
                                    <div className="col-md-2 text-end"><button className="btn btn-light" type="button"
                                        onClick={() => { navigate("/") }}>Sign out</button></div>
                                </div>
                            </div>
                            <div className="card-body" >
                                <div className="row">
                                    <div>
                                        <h6>Name</h6>
                                        <p>{name}</p>
                                    </div>
                                    <div >
                                        <h6>Email</h6>
                                        <p>{email}</p>
                                    </div>
                                    <div >
                                        <h6>Contact</h6>
                                        <p>{contact}</p>
                                    </div>
                                    <div >
                                        <h6>Address</h6>
                                        <p>{address}
                                        </p>
                                    </div>
                                    <div className="mt-3">
                                        <button className="btn btn-primary " type="button" data-bs-toggle="modal" data-bs-target="#editModal">Edit</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="modal fade" id="editModal" tabIndex="-1">
                <div className="modal-dialog modal-lg">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">Edit Profile</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                            <form onSubmit={(e) => { e.preventDefault(); update(profile.id) }}>
                                <div >
                                    <label className="form-label">Name</label>
                                    <input type="text" className="form-control" value={name}
                                        onChange={(e) => { setName(e.target.value) }} required />
                                </div>
                                <div >
                                    <label className="form-label">Email</label>
                                    <input type="email" className="form-control" value={email}
                                        onChange={(e) => { setEmail(e.target.value) }} required />
                                </div>
                                <div >
                                    <label className="form-label">Contact</label>
                                    <input type="tel" className="form-control" value={contact}
                                        onChange={(e) => { setContact(e.target.value) }} required />
                                </div>
                                <div >
                                    <label className="form-label">Address</label>
                                    <textarea className="form-control" rows="4" defaultValue={address}
                                        onChange={(e) => { setAddress(e.target.value) }}></textarea>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="submit" className="btn btn-primary" data-bs-dismiss="modal">Save changes</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default Profile;