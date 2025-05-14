import { useState } from "react";
import users from '../../data/users';
import { Link, useNavigate } from "react-router";
import axios from "axios";

function Login() {
    const [username, setUsername] = useState(null);
    const [password, setPassword] = useState(null);
    const [msgUsername, setMsgUsername] = useState(null);
    const [msgPassword, setMsgPassword] = useState(null);
    const [userData, setUserData] = useState(users);
    const navigate = useNavigate();

    const login = () => {
        let isCorrect = false;

        if (username === null || username === "" || username === undefined) {
            setMsgUsername("Username cannot be blank")
            return
        }
        else {
            setMsgUsername(null)
        }

        if (password == null || password === "" || password === undefined) {
            setMsgPassword("Password cannot be blank")
            return
        }
        else {
            setMsgPassword(null)
        }
        //call the api to generate token
        let body = {
            "username": username,
            "password": password
        }

        axios.post('http://localhost:8081/api/auth/token/generate', body)
            .then(response => {
                //console.log(response)
                let token = response.data.token
                //save the token in localstorage memory of web browser 
                localStorage.setItem('token', token)
                localStorage.setItem('username', username)

                //console.log(token)
                axios.get('http://localhost:8081/api/auth/user/details',
                    {
                        headers: {
                            "Authorization": `Bearer ${token}`  //token goes here but not getting detected in backend
                        }
                    }
                )
                    .then(resp => {
                        //console.log(resp)
                        switch (resp.data.role) {
                            case 'CUSTOMER':
                                //navigate to customer dashboard
                                navigate("/customer")
                                break;
                            case 'VENDOR':
                                //navigate to vendor dashboard
                                navigate("/vendor")
                                break;
                            case 'ADMIN':
                                //navigate to executive dashboard
                                break;
                            default:
                                break;
                        }
                    })
                    .catch(err => {
                        setMsgUsername("Invalid Credentials")
                        console.log(err)
                    })
            })
            .catch(err => {
                setMsgUsername("Invalid Credentials")
                console.log(err)
            })
        //check username password by calling API else use file data 
        // userData.forEach(u => {
        //     if (u.username === username && u.password === password) {
        //         isCorrect = true
        //         switch (u.role) {
        //             case 'CUSTOMER':
        //                 navigate("/customer")
        //                 break;
        //             case 'VENDOR':

        //                 break;
        //             case 'EXECUTIVE':

        //                 break;


        //             default:
        //                 break;
        //         }
        //     }
        // }
        // );
        // if (isCorrect === false) {
        //     setMsgUsername("Invalid Credentials")
        // }

    }

    return (
        <div>
            <div className="container-fluid">

                <div className="row mb-4">
                    <div className="col-lg-12">
                        <nav class="navbar navbar-light bg-light">
                            <div class="container-fluid">
                                <span class="navbar-brand mb-0 h1">Navbar</span>
                            </div>
                        </nav>
                    </div>
                </div>
                <br /><br /><br />
                <div className="row mt-4">
                    <div className="col-sm-4">

                    </div>
                    <div className="col-sm-4">
                        <div className="card">
                            <div className="card-header">
                                Login
                            </div>
                            <div class="card-body">
                                {
                                    msgUsername === null ? "" : <div className="mb-4">
                                        {msgUsername}
                                    </div>
                                }
                                {
                                    msgPassword === null ? "" : <div className="mb-4">
                                        {msgPassword}
                                    </div>
                                }
                                <div className="mb-4">
                                    <label>Username: </label>
                                    <input type="text" className="form-control"
                                        onChange={$event => {
                                            setUsername($event.target.value);
                                            setMsgUsername(null)
                                        }} />
                                </div>
                                <div className="mb-4">
                                    <label>Password: </label>
                                    <input type="password" className="form-control"
                                        onChange={($event) => {
                                            setPassword($event.target.value);
                                            setMsgPassword(null)
                                        }} />
                                </div>
                                <div className="mb-4">
                                    <button type="button" class="btn btn-primary" onClick={() => { login() }}>Login</button>
                                </div>
                            </div>
                            <div className="card-footer">
                                Don't have an Account? <br />
                                <Link to="/customer/signup"> Sign Up as Customer </Link> <br />
                                Sign Up as Seller <br />
                                Reset Password
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
export default Login