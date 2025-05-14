import axios from "axios";
import { useState } from "react";

function StudentPost() {

    const [name, setName] = useState(null);
    const [email, setEmail] = useState(null);
    const [age, setAge] = useState(null);

    const addStudent = async ($event) => {
        $event.preventDefault();
        let obj = {
            "name": name,
            "email": email,
            "age": age
        }
        let response = await axios.post("http://localhost:8081/api/student/add", obj);
        console.log(response);
    }
    return (
        <div>
            <div className="card">
                <div className="card-header">
                    <h4>Student Details</h4>
                </div>
                <div className="card-body">
                    <form>
                        <div className="mb-4">
                            <label>Enter Student Name </label>
                            <input type="text"
                                onChange={($event) => { setName($event.target.value) }} />
                        </div>
                        <div className="mb-4">
                            <label>Enter Student Email </label>
                            <input type="text"
                                onChange={($event) => { setEmail($event.target.value) }} />
                        </div>
                        <div className="mb-4">
                            <label>Enter Student Age </label>
                            <input type="number"
                                onChange={($event) => { setAge($event.target.value) }} />
                        </div>
                        <div className="mb-4">
                            <button className="btn btn-primary"
                                onClick={($event) => { addStudent($event) }}>Save</button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    )
}
export default StudentPost;