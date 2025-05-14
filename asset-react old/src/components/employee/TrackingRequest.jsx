import "./TrackingRequest.css"
import Sidebar from './Sidebar'
function TrackingRequest(){
    return(
        <div>
            <div class="container-fluid">
        <div class="row">
            {/* <!-- Sidebar --> */}
             <Sidebar/>
            {/* <!-- Main Content --> */}
            <div class="col-md-10 p-4">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card mb-4">
                            <div class="card-header secondary-bg text-white">
                                Asset Requests
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Asset Name</th>
                                                <th>Request Date</th>
                                                <th>Status</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>MacBook Pro</td>
                                                <td>2023-08-15</td>
                                                <td><span class="badge bg-warning">Pending</span></td>
                                                <td>
                                                    <button class="btn btn-sm btn-primary">View Details</button>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>iPhone 14 Pro</td>
                                                <td>2023-07-20</td>
                                                <td><span class="badge bg-success">Approved</span></td>
                                                <td>
                                                    <button class="btn btn-sm btn-primary">View Details</button>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Cash Reserves</td>
                                                <td>2023-08-10</td>
                                                <td><span class="badge bg-danger">Rejected</span></td>
                                                <td>
                                                    <button class="btn btn-sm btn-primary">View Details</button>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
        </div>
    )
}
    
export default TrackingRequest;