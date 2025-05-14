// import { useParams } from "react-router";
// import "./css/NewAssetRequest.css";
// import Sidebar from './Sidebar';
// import axios from "axios";
// import { useState } from "react";
// import DatePicker from "react-datepicker";
// import "react-datepicker/dist/react-datepicker.css";

// function NewAssetRequest() {
//     const [requestDate, setRequestDate] = useState(null);
//     const [reason, setReason] = useState("");
//     const { assetId } = useParams();

//     const postRequest = async ($event) => {
//         $event.preventDefault();
//         let requestDate = new Date();   
 
//         let formattedDate = requestDate ? requestDate.toISOString().split('T')[0] : null;
 
//         let obj = {
//             'requestDate': formattedDate,  
//             'reason': reason
//         };


//         console.log("Request Payload:", obj);

//         try {
//             const response = await axios.post(`http://localhost:8081/api/assetrequest/add/${assetId}`, obj);
//             console.log(response);
//         } catch (error) { 
//         }
//     }

//     return (
//         <div className="container-fluid">
//             <div className="row">
//                 {/* Sidebar */}
//                 <Sidebar />
//                 <div className="col-md-9 p-4">
//                     <h3>New Asset Request</h3> 
//                     <form onSubmit={($event)=>{postRequest($event)}}>
//                         <div className="mb-4">
//                             <label>Enter Request Date: </label>
//                             <DatePicker
//                                 selected={requestDate}
//                                 onChange={(date) => setRequestDate(date)}
//                                 className="form-control"
//                                 dateFormat="yyyy-MM-dd"
//                                 placeholderText="Select a date"
//                             />
//                         </div>
//                         <div className="mb-4">
//                             <label>Enter Reason for Request: </label>
//                             <textarea
//                                 onChange={($event) => setReason($event.target.value)}
//                                 value={reason}
//                                 className="form-control"
//                                 rows="5"
//                             />
//                         </div>
//                         <div className="mb-4">
//                             <input
//                                 type="submit"
//                                 value="Post Request"
//                                 className="btn btn-primary"
//                             />
//                         </div>
//                     </form>
//                 </div>
//             </div>
//         </div>
//     );
// }

// export default NewAssetRequest;
