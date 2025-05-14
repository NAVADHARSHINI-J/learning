import axios from "axios";
import { useEffect } from "react";
import { useState } from "react"

function StudentList() {
    const [students, setStudents] = useState([]);
    const [totalpage, setTotalPage] = useState(0);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(3);
    const [pageArray, setPageArray] = useState([]);
    const [name, setName] = useState(null);
    const [age, setAge] = useState(0);
    //get the data by calling the api
    const getStudents = async () => {
        try {
            let response = await axios.get(`http://localhost:8081/api/student/all?page=${page}&size=${size}`);

            setStudents(response.data.list);
            setTotalPage(response.data.totalPages);
            let tp = response.data.totalPages;
            setPageArray([]);
            let temp = [];
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
    //get all by using the pagination
    useEffect(() => {
        getStudents();
    }, [page])
    //delete the student
    const deleteStudent = async (studentId) => {
        try {
            //call the api
            await axios.delete(`http://localhost:8081/api/student/delete/${studentId}`);
            //delete the record from ui
            let temp = [...students];
            temp = temp.filter(s => s.id !== studentId);
            setStudents(temp);
        }
        catch (err) {
            console.log(err);
        }
        // getStudents()   don't call api untill it is necessary
    }
    //function to update students
    const updateStudent = async (studentId) => {
        //call the api
        try {
            let studentObj = {
                "name": name,
                "age": age
            }
            const response = await axios.put('http://localhost:8081/api/student/update/' + studentId
                , studentObj);
            //appear in the ui
            //delete the element
            let temp = [...students];
            temp = temp.filter(s => s.id !== studentId);
            //add the new element to temp
            studentObj = {
                "id": studentId,
                "name": name,
                "age": age,
                "email": response.data.email
            }
            temp.push(studentObj)
            setStudents(temp)
        }
        catch (err) {
            console.log(err);
        }
    }
    return (
        <div>
            <h3>All Students</h3>
            <div className="container">
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Name</th>
                            <th scope="col">Email</th>
                            <th scope="col">Age</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            students.map((s, index) => (
                                <tr key={index}>
                                    <th scope="row">{s.id}</th>
                                    <td>{s.name}</td>
                                    <td>{s.email}</td>
                                    <td>{s.age}</td>
                                    <td>
                                        <button className="btn btn-danger btn-sm " onClick={() => { deleteStudent(s.id) }}>Delete</button>
                                        &nbsp;&nbsp;
                                        <button className="btn btn-info btn-sm" data-bs-toggle="modal"
                                            data-bs-target={`#update-${s.id}`}>Update</button>
                                        &nbsp;&nbsp;
                                        <button className="btn btn-secondary btn-sm">Edit</button>
                                    </td>
                                    {/* // <!-- Modal --> */}
                                    <div class="modal fade" id={`update-${s.id}`} tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <form>
                                                        <div className="mb-4">
                                                            <label>Enter Student Name </label>
                                                            <input type="text" placeholder={s.name} className="form-control"
                                                                onChange={($event) => { setName($event.target.value) }} />
                                                        </div>
                                                        <div className="mb-4">
                                                            <label>Enter Student Age </label>
                                                            <input type="number" placeholder={s.age} className="form-control"
                                                                onChange={($event) => { setAge($event.target.value) }} />
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                    <button type="button" class="btn btn-primary" onClick={() => updateStudent(s.id)}>Save changes</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
                <div className="row">
                    <div className="col-sm-4">

                    </div>
                    <div className="col-sm-4">
                        <nav aria-label="Page navigation example">
                            <ul className="pagination">
                                <li className="page-item"><a className="page-link" href="#"
                                    onClick={() => { page === 0 ? setPage(0) : setPage(page - 1) }}>Previous</a></li>
                                {
                                    pageArray.map((e, index) => (
                                        <li className="page-item" key={index}><a className="page-link" href="#"
                                            onClick={() => { setPage(index) }}>{index + 1}</a></li>
                                    ))
                                }
                                <li className="page-item"><a className="page-link" href="#"
                                    onClick={() => { page === totalpage - 1 ? setPage(totalpage - 1) : setPage(page + 1) }}>Next</a></li>
                            </ul>
                        </nav>
                    </div>
                    <div className="col-sm-4">

                    </div>
                </div>
            </div>
        </div>
    )
}
export default StudentList;