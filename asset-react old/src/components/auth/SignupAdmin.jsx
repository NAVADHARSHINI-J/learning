import axios from "axios";
import { useState } from "react";
import { Link, useNavigate } from "react-router";

function SignupAdmin() {
    const [name, setName] = useState();
    const [email, setEmail] = useState();
    const [contact, setContact] = useState();
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();
    const [address, setAddress] = useState();
    const navigate = useNavigate();
    const signUp = async () => {
        try {
            const response = await axios
                .post("http://localhost:8081/api/admin/add", {
                    "name": name,
                    "email": email,
                    "contact": contact,
                    "address": address,
                    "user": {
                        "username": username,
                        "password": password,
                        "role": "ADMIN"
                    }
                })
            console.log("sign up is successfull..")
            navigate("/")
        }
        catch (err) {
            console.log(err);
        }
    }

    return (
        <div>
            <div className="d-flex justify-content-center align-items-center min-vh-100" style={{ backgroundColor: "#f4f4f4" }}>
                <div className="container-fluid">
                    <div className="row mt-4">
                        <div className="col-sm-4">
                        </div>
                        <div className="col-sm-4">
                            <div className="card" >
                                <div className="card-header text-center" style={{ backgroundColor: "#2E7893" }}>
                                    <h3 style={{ color: "white", fontFamily: 'Georgia' }}>Hexa Assets</h3>
                                    <h5 style={{ color: "white" }}>Asset Management System</h5>
                                </div>
                                <div className="card-body">
                                    <form className="row g-3" onSubmit={($e) => signUp($e)}>
                                        <div className="col-md-6">
                                            <label className="form-label">Name</label>
                                            <input type="text" className="form-control" id="input"
                                                onChange={($event) => { setName($event.target.value) }} />
                                        </div>
                                        <div className="col-md-6">
                                            <label className="form-label">Email</label>
                                            <input type="text" className="form-control" id="inputEmail4"
                                                onChange={($event) => { setEmail($event.target.value) }} />
                                        </div>
                                        <div className="col-md-6">
                                            <label className="form-label">Contact</label>
                                            <input type="number" className="form-control" id="inputPassword"
                                                onChange={($event) => { setContact($event.target.value) }} />
                                        </div>

                                        <div className="col-md-6">
                                            <label className="form-label">Username</label>
                                            <input type="text" className="form-control" id="inputEmail"
                                                onChange={($event) => { setUsername($event.target.value) }} />
                                        </div>
                                        <div className="col-md-6">
                                            <label className="form-label">Password</label>
                                            <input type="password" className="form-control" id="inputPasswor"
                                                onChange={($event) => { setPassword($event.target.value) }} />
                                        </div>
                                        <div className="col-md-6">

                                        </div>
                                        <div className="col-md-12">
                                            <label className="form-label">Address</label>
                                            <textarea className="form-control" id="address" rows="3"
                                                onChange={($event) => { setAddress($event.target.value) }}></textarea>
                                        </div>
                                    </form>
                                </div>
                                <div className="mb-4 text-center">
                                    <button type="button" className="btn btn-primary "
                                        onClick={() => { signUp() }}>Sign Up</button>
                                </div>
                            </div>
                            <div className="card-footer" style={{ backgroundColor: "#2E7893", color: "white" }}>
                                <p > Already have an Account? <Link to={"/"} style={{ color: "white" }}>Sign In </Link><br /></p>

                            </div>
                        </div>
                    </div>
                    <div className="col-sm-4">

                    </div>

                </div>

            </div>
        </div>
    )
}
export default SignupAdmin