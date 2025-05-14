import './LiquidAsset.css';

function LiquidAssetAllocation() {
  return (
    <div className="container-fluid">
      <div className="row">
        {/* Sidebar */}
        <div className="col-md-2 sidebar">
          <div className="text-center text-white mb-4">
            <h3>HexaAssets</h3>
          </div>
          <ul className="nav flex-column">
            <li className="nav-item">
              <a className="nav-link" href="manager">
                <i className="bi bi-speedometer2 me-2"></i>Dashboard
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/dashassetpage">
                <i className="bi bi-cash-stack me-2"></i>Liquid Assets
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/dashassetreq">
                <i className="bi bi-plus-circle me-2"></i>Liquid Asset Request
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link active" href="/dashassetall">
                <i className="bi bi-clipboard-check me-2"></i>Liquid Asset Allocation
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/dashassetunall">
                <i className="bi bi-archive me-2"></i>Unallocated Liquid Assets
              </a>
            </li>
          </ul>
        </div>

        {/* Main Content */}
        <div className="col-md-10 p-4">
          <div className="card">
            <div className="card-header">
              <span>Liquid Asset Allocation</span>
              <form className="d-flex" style={{ maxWidth: '300px' }} method="get">
                <input
                  type="text"
                  className="form-control form-control-sm"
                  name="search"
                  placeholder="Search requests..."
                />
                <button type="submit" className="btn btn-sm btn-outline-light">
                  <i className="bi bi-search"></i>
                </button>
              </form>
            </div>
            <div className="card-body">
              <table className="table table-striped">
                <thead>
                  <tr>
                    <th>Asset Type</th>
                    <th>Total Amount</th>
                    <th>Allocated Amount</th>
                    <th>Remaining Amount</th>
                    <th>Allocation Percentage</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Cash Reserves</td>
                    <td>$500,000</td>
                    <td>$350,000</td>
                    <td>$150,000</td>
                    <td>70%</td>
                    <td><span className="badge bg-success">Optimal</span></td>
                  </tr>
                  <tr>
                    <td>Short-Term Investments</td>
                    <td>$250,000</td>
                    <td>$100,000</td>
                    <td>$150,000</td>
                    <td>40%</td>
                    <td><span className="badge bg-warning">Moderate</span></td>
                  </tr>
                  <tr>
                    <td>Money Market Funds</td>
                    <td>$350,000</td>
                    <td>$200,000</td>
                    <td>$150,000</td>
                    <td>57%</td>
                    <td><span className="badge bg-success">Optimal</span></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LiquidAssetAllocation;