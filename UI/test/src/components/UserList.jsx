import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router";

function UserList() {
    const [user, setUser] = useState([])
    useEffect(() => {
        const getData = async () => {
            try {
                const response = await axios.get("https://jsonplaceholder.typicode.com/users")
                setUser(response.data);
            } catch (error) {
                console.log(error)
            }
        }
        getData()
    }, [])
    //function to delete
    const deleteUser = async (userId) => {
        // console.log(userId)
        try {
            const response = await axios.delete(`https://jsonplaceholder.typicode.com/users/${userId}`)
            let temp = [...user]
            temp = temp.filter(u => u.id !== userId)
            setUser(temp)
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
                    <div className="card-body">
                        <div className="row">
                            <div className="col-sm-12 text-end">
                                <Link to="/add-user"><button className="btn btn-primary">Add User</button></Link>
                            </div>
                        </div>
                        <br />
                        <div className="row">
                            <table className="table table-primary table-striped">
                                <thead>
                                    <tr>
                                        <th scope="col">Name</th>
                                        <th scope="col">Email</th>
                                        <th scope="col">Phone</th>
                                        <th scope="col">Company Name</th>
                                        <th scope="col">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        user.map((u, index) => (
                                            <tr key={index}>
                                                <th scope="row">{u.name}</th>
                                                <td>{u.email}</td>
                                                <td>{u.phone}</td>
                                                <td>{u.company.name}</td>
                                                <td><button className="btn btn-secondary"
                                                    onClick={() => { deleteUser(u.id) }}>Delete</button></td>
                                            </tr>

                                        ))
                                    }

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )

}
export default UserList