import './LiquidAsset.css';
import { Link } from "react-router-dom";

function LiquidAssetDashboard() {
    return (
        <div class="container-fluid">
            <div class="row">
                {/* <!-- Sidebar --> */}
                <div class="col-md-2 sidebar">
                    <div class="text-center text-white mb-4">
                        <h3>HexaAssets</h3>
                    </div>
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link active" href="manager">
                                <i class="bi bi-speedometer2 me-2"></i>Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/dashassetpage">
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

                {/* <!-- Main Content --> */}
                <div class="col-md-10 p-4">
                    <center><h1 class="mb-4">Liquid Assets Dashboard</h1></center>

                    {/* <!-- Quick Stats --> */}
                    <div class="quick-stats">
                        <div class="card stat-card bg-primary text-white">
                            <div class="card-body">
                                <h5>Total Liquid Assets</h5>
                                <h3>$1,100,000</h3>
                            </div>
                        </div>
                        <div class="card stat-card bg-success text-white">
                            <div class="card-body">
                                <h5>Available Balance</h5>
                                <h3>$950,000</h3>
                            </div>
                        </div>
                        <div class="card stat-card bg-warning text-white">
                            <div class="card-body">
                                <h5>Pending Withdrawals</h5>
                                <h3>$75,000</h3>
                            </div>
                        </div>
                    </div>

                    {/* <!-- Asset Requests and Liquid Assets Sections --> */}
                    <div class="row">
                        {/* <!-- Asset Requests Section --> */}
                        <div class="col-md-6">
                            <div class="card dashboard-card">
                                <div class="card-header">
                                    <span>Recent Requests</span>
                                    <a href="/dashassetreq" class="btn btn-sm btn-light">View All</a>
                                </div>
                                <div class="card-body">
                                    <ul class="list-group">
                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                            John Doe
                                            <span class="badge bg-warning">Pending</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                            Jane Smith
                                            <span class="badge bg-warning">Pending</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                            Mike Johnson
                                            <span class="badge bg-success">Approved</span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        {/* <!-- Liquid Assets Section --> */}
                        <div class="col-md-6">
                            <div class="card dashboard-card">
                                <div class="card-header">
                                    <span>Liquid Assets</span>
                                    <a href="/dashassetpage" class="btn btn-sm btn-light">View All</a>
                                </div>
                                <div class="card-body">
                                    <ul class="list-group">
                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                            Maintainance charges
                                            <span class="badge bg-success">$500,000</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                            Flight charges
                                            <span class="badge bg-warning">$250,000</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                            Room charges
                                            <span class="badge bg-success">$350,000</span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default LiquidAssetDashboard;