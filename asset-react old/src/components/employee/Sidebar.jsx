import { Link, useLocation } from 'react-router';
import './css/Sidebar.css'
function Sidebar() {

    const location = useLocation();
    const currentPath = location.pathname;


    return (
        <div className="col-md-2 sidebar text-white">
            <div className="text-center mb-4">
                <h3>HexaAssets</h3>
            </div>
            <ul className="nav flex-column">
                <li className="nav-item">
                    <Link to={'/employee/homeassets'} className={`nav-link ${currentPath === '/employee/homeassets' ? 'active' : ''}`} >
                        <i className="bi bi-house me-2"></i>Home Assets
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to={'/employee/liquidassets'} className={`nav-link ${currentPath === '/employee/liquidassets' ? 'active' : ''}`}>
                        <i className="bi bi-cash-coin me-2"></i>Liquid Assets
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to={'/employee/allocatedassets'} className={`nav-link ${currentPath === '/employee/allocatedassets' ? 'active' : ''}`}>
                        <i className="bi bi-clipboard-check me-2"></i>Allocated Assets
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to={'/employee/newassetrequest'} className={`nav-link ${currentPath === '/employee/newassetrequest' ? 'active' : ''}`}>
                        <i className="bi bi-plus-circle me-2"></i>New Asset Request
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to={'/employee/trackingrequest'} className={`nav-link ${currentPath === '/employee/trackingrequest' ? 'active' : ''}`}>
                        <i className="bi bi-truck me-2"></i>Tracking Request
                    </Link>
                </li>
            </ul>
        </div>
    )
}

export default Sidebar;