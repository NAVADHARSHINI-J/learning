import { useEffect, useState } from "react"
import CustomerNavbar from "./CustomerNavbar"
import axios from "axios";
import { Link } from "react-router";


function CustomerDashboard() {
    const [category, setCategory] = useState([]);

    useEffect(() => {
        //make a method to call a api
        const getCategory = async () => {
            //call the api
            try {
                const response = await axios.get("http://localhost:8081/api/category/all");
                setCategory(response.data);
                console.log(response)
            }
            catch (err) {
                console.log(err);
            }
        }
        getCategory();
    }, [])
    return (
        <div>
            <h1>Customer dashboard</h1>
            <div className="container-fluid">
                <div className="row">
                    <div className="col-lg-12">
                        <CustomerNavbar />
                    </div>
                </div>

                <div className="row">
                    <div className="col-lg-12">
                        <h2>Featured Categories</h2>
                        <div className="row">
                            {
                                category.map((c, index) => (
                                    <div className="col-sm-4" key={index}>
                                        <Link to={`/product/${c.id}/${c.name}`}>
                                            <div className="card">
                                                <div className="card-body">
                                                    {c.name}
                                                </div>
                                            </div>
                                        </Link>
                                    </div>
                                ))
                            }
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default CustomerDashboard
