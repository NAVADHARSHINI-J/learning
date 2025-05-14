import { Link, useLocation } from "react-router-dom";
import './css/Sidebar.css';

function Sidebar() {
    const location = useLocation();

    return (
        <div className="col-md-2 sidebar flex-column py-4">
            <div className="text-center mb-4">
                <h3 className="text-white">HexaAssets Admin</h3>
            </div>
            <ul className="nav flex-column flex-grow-1">
                <li className="nav-item">
                    <Link
                        to="/admin"
                        className={location.pathname === "/admin" ? "nav-link active" : "nav-link"}>
                        <i className="bi bi-speedometer2 me-2"></i>Dashboard
                    </Link>
                </li>
                <li className="nav-item">
                    <Link
                        to="/admin/employeelist"
                        className={location.pathname === "/admin/employeelist" ? "nav-link active" : "nav-link"}>
                        <i className="bi bi-people me-2"></i>Employees
                    </Link>
                </li>
                <li className="nav-item">
                    <Link
                        to="/admin/assetlist"
                        className={location.pathname === "/admin/assetlist" ? "nav-link active" : "nav-link"}>
                        <i className="bi bi-laptop me-2"></i>Assets
                    </Link>
                </li>
                <li className="nav-item">
                    <Link
                        to="/admin/request"
                        className={location.pathname === "/admin/request" ? "nav-link active" : "nav-link"}>
                        <i className="bi bi-file-earmark-text me-2"></i>Requests
                    </Link>
                </li>
                <li className="nav-item mt-auto profile">
                    <Link
                        to="/admin/profile"
                        className={location.pathname === "/admin/profile" ? "nav-link active" : "nav-link"}>
                        <i className="bi bi-person me-2"></i>Profile
                    </Link>
                </li>
            </ul>
        </div>
    );
}

export default Sidebar;
