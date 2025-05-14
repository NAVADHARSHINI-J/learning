import axios from "axios";
import { useEffect, useState } from "react";

function VendorDashboard() {

    const [categories, setCategories] = useState([]);
    const [warehouses, setWarehouses] = useState([]);
    const [title, setTitle] = useState(null);
    const [description, setDescription] = useState(null);
    const [shortDescription, setShortDescription] = useState(null);
    const [price, setPrice] = useState(null);
    const [cid, setcid] = useState(null);
    const [wid, setwid] = useState(null);
    const [product, setProduct] = useState({});
    const [showProductForm, setShowProductForm] = useState(true);
    const [showImageForm, setShowImageForm] = useState(false);
    const [pImage, setPImage] = useState(null);


    useEffect(() => {
        const getCategories = async () => {

            try {
                const response = await axios.get("http://localhost:8081/api/category/all")
                setCategories(response.data)
            } catch (error) {
                console.log(error)
            }
        }
        const getWarehouses = async () => {
            try {
                const response = await axios.get("http://localhost:8081/api/warehouse/getall")
                setWarehouses(response.data)
            } catch (error) {
                console.log(error)
            }
        }
        getCategories()
        getWarehouses()
    }, [])
    const add = async (e) => {
        e.preventDefault();
        let body = {
            "title": title,
            "description": description,
            "shortDescription": shortDescription,
            "imageUrl": "",
            "price": price
        }
        let token = localStorage.getItem("token")
        let header = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        const response = await axios.post(`http://localhost:8081/api/product/add/${cid}/${wid}`,
            body, header
        )
        setProduct(response.data)
        console.log(response)
        setShowProductForm(false)
        setShowImageForm(true)
    }

    const upload = async () => {
        if (!pImage) {
            alert('Image not selected');
            return
        }
        //create formData to carry your image in it 
        const formData = new FormData();
        formData.append("file", pImage);
        let token = localStorage.getItem('token');

        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`  //token goes here but not getting detected in backend
            }
        }
        //pass this form data to your API 
        const resp = await axios.post(`http://localhost:8081/api/product/image/upload/${product.id}`
            , formData, headers
        )
        console.log(resp)
        alert('Image uploaded....')
    }

    return (
        <div>
            <h2>Vendor DashBoard</h2>
            <div className="container">
                <div className="card">
                    <div className="card-header">
                        <h4>Add Product</h4>
                    </div>
                    <div className="card-body">
                        {showProductForm && <form onSubmit={(e) => { add(e) }} class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label">Title</label>
                                <input type="text" class="form-control"
                                    onChange={(e) => setTitle(e.target.value)} />
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Price</label>
                                <input type="number" class="form-control"
                                    onChange={(e) => setPrice(e.target.value)} />
                            </div>
                            <div class="col-12">
                                <label class="form-label">Short Description</label>
                                <input type="text" class="form-control" placeholder="Enter product highlights"
                                    onChange={(e) => setShortDescription(e.target.value)} />
                            </div>
                            <div class="col-12">
                                <label class="form-label">Full Description</label>
                                <textarea className="form-control" rows={10} cols={50}
                                    onChange={(e) => setDescription(e.target.value)}></textarea>
                            </div>
                            <div class="col-md-6">
                                <label for="inputState" class="form-label">Category</label>
                                <select id="inputState" class="form-select"
                                    onChange={(e) => setcid(e.target.value)}>
                                    <option selected>Choose...</option>
                                    {categories.map((c, index) => (
                                        <option key={index} value={c.id}>{c.name}</option>
                                    ))}
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label for="inputState" class="form-label">Warehouse</label>
                                <select id="inputState" class="form-select"
                                    onChange={(e) => setwid(e.target.value)}>
                                    <option selected>Choose...</option>
                                    {warehouses.map((w, index) => (
                                        <option key={index} value={w.id}>{w.name}</option>
                                    ))}
                                </select>
                            </div>
                            <div class="col-12">
                                <button type="submit" class="btn btn-primary">Save</button>
                            </div>
                        </form>}
                        {
                            showImageForm ? <div>
                                <div className="col-md-12">
                                    <label className="form-label">Upload Product Image</label>
                                    <input type="file" className="form-control" onChange={(e) => {
                                        setPImage(e.target.files[0])
                                    }}
                                    />

                                    <br />
                                    <button className="btn btn-info" onClick={() => upload()}>
                                        Upload Product Image
                                    </button>
                                </div>
                            </div> : ""
                        }
                    </div>
                </div>

            </div>
        </div>
    )
}
export default VendorDashboard;