
import { Routes, Route } from "react-router-dom"
import { useEffect } from "react"
import { useDispatch } from "react-redux"
import AssetList from "./components/admin/AssetList"
import EmployeeList from "./components/admin/EmployeeList"
import Profile from "./components/admin/Profile"
import Login from "./components/auth/Login"
import AdminDashboard from "./components/admin/AdminDashboard"
import RequestList from "./components/admin/RequestList"
import SignupAdmin from "./components/auth/SignupAdmin"
import fetchProfile from "./store/actions/ProfileAction"
import LiquidAssetDashboard from './components/manager/LiquidAssetDashboard';
import LiquidAssetPage from './components/manager/LiquidAssetPage';
import LiquidAssetRequest from './components/manager/LiquidAssetRequest';
import LiquidAssetAllocation from './components/manager/LiquidAssetAllocation';
import LiquidAssetUnallocated from './components/manager/LiquidAssetUnallocated';


function App() {
  const dispatch = useDispatch()
  useEffect(() => {
    dispatch(fetchProfile())
  }, [])

  return (
    <div>
      <Routes>
        <Route index path="" element={<Login />} />
        <Route path="admin" element={<AdminDashboard />} />
        <Route path="admin/employeelist" element={<EmployeeList />} />
        <Route path="admin/assetlist" element={<AssetList />} />
        <Route path="admin/request" element={<RequestList />} />
        <Route path="admin/profile" element={<Profile />} />
        <Route path="signupadmin" element={<SignupAdmin />} />
        <Route path="manager" element={<LiquidAssetDashboard />} />
        <Route path="/dashassetpage" element={<LiquidAssetPage />} />
        <Route path="/dashassetreq" element={<LiquidAssetRequest />} />
        <Route path="/dashassetall" element={<LiquidAssetAllocation />} />
        <Route path="/dashassetunall" element={<LiquidAssetUnallocated />} />
      </Routes>
    </div>
  )
}

export default App
