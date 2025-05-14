import './LiquidAsset.css'

function LiquidAssetPage() {
    return (
        <div>
            <div class="container-fluid">
                <div class="row">
                    {/* <!-- Sidebar --> */}
                    <div class="col-md-2 sidebar">
                        <div class="text-center text-white mb-4">
                            <h3>HexaAssets</h3>
                        </div>
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link" href="manager">
                                    <i class="bi bi-speedometer2 me-2"></i>Dashboard
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" href="/dashassetpage">
                                    <i class="bi bi-cash-stack me-2"></i>Liquid Assets
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/dashassetreq">
                                    <i class="bi bi-plus-circle me-2"></i>Liquid Asset Request
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/dashassetall">
                                    <i class="bi bi-clipboard-check me-2"></i>Liquid Asset Allocation
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/dashassetunall">
                                    <i class="bi bi-archive me-2"></i>Unallocated Liquid Assets
                                </a>
                            </li>
                        </ul>
                    </div>

                    <div class="col-md-10 p-4">
                        <div class="card">
                            <div class="card-header">
                                <span>Liquid Assets List</span>
                                <button class="btn btn-sm btn-light" data-bs-toggle="modal" data-bs-target="#addAssetModal">
                                    <i class="bi bi-plus me-1"></i>Add Assets
                                </button>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    {/* <!-- Original Assets --> */}
                                    <div class="col-md-4 mb-3">
                                        <div class="card asset-card">
                                            <div class="card-body">
                                                <h5 class="card-title">Maintenance Charges</h5>
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <span class="badge bg-success">$500,000 Available</span>
                                                    <a href="#maintenanceModal" class="btn btn-sm btn-outline-primary"
                                                        data-bs-toggle="modal">View</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <div class="card asset-card">
                                            <div class="card-body">
                                                <h5 class="card-title">Flight Charges</h5>
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <span class="badge bg-warning">$250,000 Limited</span>
                                                    <a href="#flightModal" class="btn btn-sm btn-outline-primary"
                                                        data-bs-toggle="modal">View</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <div class="card asset-card">
                                            <div class="card-body">
                                                <h5 class="card-title">Room Charges</h5>
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <span class="badge bg-success">$350,000 Available</span>
                                                    <a href="#roomModal" class="btn btn-sm btn-outline-primary"
                                                        data-bs-toggle="modal">View</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    {/* <!-- New Additional Assets --> */}
                                    <div class="col-md-4 mb-3">
                                        <div class="card asset-card">
                                            <div class="card-body">
                                                <h5 class="card-title">IT Infrastructure</h5>
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <span class="badge bg-primary">$750,000 Allocated</span>
                                                    <a href="#itModal" class="btn btn-sm btn-outline-primary"
                                                        data-bs-toggle="modal">View</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <div class="card asset-card">
                                            <div class="card-body">
                                                <h5 class="card-title">Marketing Budget</h5>
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <span class="badge bg-info">$450,000 Available</span>
                                                    <a href="#marketingModal" class="btn btn-sm btn-outline-primary"
                                                        data-bs-toggle="modal">View</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <div class="card asset-card">
                                            <div class="card-body">
                                                <h5 class="card-title">Research & Development</h5>
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <span class="badge bg-danger">$600,000 Critical</span>
                                                    <a href="#rdModal" class="btn btn-sm btn-outline-primary"
                                                        data-bs-toggle="modal">View</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            {/* <!-- Existing Modals (Maintenance, Flight, Room) remain the same --> */}
            <div class="modal fade" id="maintenanceModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Maintenance Charges Details</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <p>Funds allocated for property maintenance, repairs, and upkeep. Comprehensive coverage for all
                                building-related expenses.</p>
                            <div class="row">
                                <div class="col">
                                    <strong>Total Amount:</strong> $500,000
                                </div>
                                <div class="col">
                                    <strong>Status:</strong> Available
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary">Edit</button>
                            <button type="button" class="btn btn-danger">Delete</button>
                        </div>
                    </div>
                </div>
            </div>

            {/* <!-- Flight Charges Modal --> */}
            <div class="modal fade" id="flightModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Flight Charges Details</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <p>Funds designated for flight-related expenses, including travel arrangements and airfare.</p>
                            <div class="row">
                                <div class="col">
                                    <strong>Total Amount:</strong> $250,000
                                </div>
                                <div class="col">
                                    <strong>Status:</strong> Limited
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary">Edit</button>
                            <button type="button" class="btn btn-danger">Delete</button>
                        </div>
                    </div>
                </div>
            </div>

            {/* <!-- Room Charges Modal --> */}
            <div class="modal fade" id="roomModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Room Charges Details</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <p>Funds allocated for accommodation expenses, covering room bookings and related services.</p>
                            <div class="row">
                                <div class="col">
                                    <strong>Total Amount:</strong> $350,000
                                </div>
                                <div class="col">
                                    <strong>Status:</strong> Available
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary">Edit</button>
                            <button type="button" class="btn btn-danger">Delete</button>
                        </div>
                    </div>
                </div>
            </div>

            {/* <!-- New Asset Modals --> */}
            <div class="modal fade" id="itModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">IT Infrastructure Details</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <p>Funds allocated for technology infrastructure, including hardware, software, and network
                                upgrades.</p>
                            <div class="row">
                                <div class="col">
                                    <strong>Total Amount:</strong> $750,000
                                </div>
                                <div class="col">
                                    <strong>Status:</strong> Allocated
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary">Edit</button>
                            <button type="button" class="btn btn-danger">Delete</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="marketingModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Marketing Budget Details</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <p>Comprehensive marketing budget for digital campaigns, branding, and promotional activities.</p>
                            <div class="row">
                                <div class="col">
                                    <strong>Total Amount:</strong> $450,000
                                </div>
                                <div class="col">
                                    <strong>Status:</strong> Available
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary">Edit</button>
                            <button type="button" class="btn btn-danger">Delete</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="rdModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Research & Development Details</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <p>Critical funding for innovation, product development, and cutting-edge research initiatives.</p>
                            <div class="row">
                                <div class="col">
                                    <strong>Total Amount:</strong> $600,000
                                </div>
                                <div class="col">
                                    <strong>Status:</strong> Critical
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary">Edit</button>
                            <button type="button" class="btn btn-danger">Delete</button>
                        </div>
                    </div>
                </div>
            </div>

            {/* <!-- Add Asset Modal --> */}
            <div class="modal fade" id="addAssetModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Add New Liquid Asset</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="mb-3">
                                    <label class="form-label">Asset Name</label>
                                    <input type="text" class="form-control" placeholder="Enter asset name" />
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Total Amount</label>
                                    <input type="number" class="form-control" placeholder="Enter total amount" />
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Status</label>
                                    <select class="form-select">
                                        <option>Available</option>
                                        <option>Limited</option>
                                        <option>Critical</option>
                                        <option>Allocated</option>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Description</label>
                                    <textarea class="form-control" rows="3" placeholder="Enter asset description"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="button" class="btn btn-primary">Add Asset</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default LiquidAssetPage;