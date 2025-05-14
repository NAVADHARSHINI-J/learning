import { Route, Routes } from "react-router"
import Login from "./components/auth/Login"
import CustomerDashboard from "./components/customer/CustomerDashboard"
import CustomerProducts from "./components/customer/CustomerProducts"
import EmployeeList from "./components/playground/Practice"
import Signup from "./components/customer/Signup"
import VendorDashboard from "./components/vendor/VendorDashboard"
import { useEffect } from "react"
import { useDispatch } from "react-redux"
import Albums from "./components/playground/Albums"
import fetchAlbums from "./store/actions/albumaction"
import fetchUsers from "./store/actions/userActions"
import UserList from "./components/playground/users"
import Dashboard from "./components/playground/Dashboard"

function App() {   //app is a component and component must have return
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(fetchAlbums())
  }, [])
  return (
    <div>

      <Routes>
        <Route index path="" element={<Login />} />
        <Route path="customer" element={<CustomerDashboard />} />
        <Route path="product/:cid/:cname" element={<CustomerProducts />} />
        <Route path="/customer/signup" element={<Signup />} />
        <Route path="/vendor" element={<VendorDashboard />} />
        <Route path="/album" element={<Albums />} />
        <Route path="/user" element={<UserList />} />
        <Route path="/chart" element={<Dashboard />} />
      </Routes>
      {/* <EmployeeList /> */}

    </div>
  )
}

export default App
