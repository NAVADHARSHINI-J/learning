import "./css/AllocatedAssets.css"
import Sidebar from "./Sidebar.jsx"
function AllocatedAssets() {
    return (
        <div>
            <div class="container-fluid">
                <div class="row">
                    {/* <!-- Sidebar --> */}
                    <Sidebar/>
                    {/* <!-- Main Content --> */}
                    <div class="col-md-10 p-4">
                        <div class="card">
                            <div class="card-header secondary-bg text-white">
                                My Allocated Assets
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Asset Name</th>
                                                <th>Serial Number</th>
                                                <th>Allocated Date</th>
                                                <th>Asset Details</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>MacBook Pro</td>
                                                <td>MBP2023-0001</td>
                                                <td>2023-07-15</td>
                                                <td>16" MacBook Pro, M2 Chip</td>
                                                <td>
                                                    <button class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#serviceRequestModal">
                                                        Service Request
                                                    </button>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>iPhone 13</td>
                                                <td>IP13-0045</td>
                                                <td>2023-06-20</td>
                                                <td>128GB, Blue Color</td>
                                                <td>
                                                    <button class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#serviceRequestModal">
                                                        Service Request
                                                    </button>
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

            {/* <!-- Service Request Modal (Same as previous implementation) --> */}
            <div class="modal fade" id="serviceRequestModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Service Request</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <form id="serviceRequestForm">
                                <div class="mb-3">
                                    <label class="form-label">Issue Description</label>
                                    <textarea class="form-control" rows="3" placeholder="Describe the issue with your asset" required></textarea>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Upload Damage Image</label>
                                    <input type="file" class="form-control"/>
                                </div>
                                <button type="submit" class="btn btn-primary">Submit Request</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    )
}
export default AllocatedAssets;