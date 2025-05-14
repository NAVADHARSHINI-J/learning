import { Route, Routes } from "react-router"
import UserList from "./components/UserList"
import AddUser from "./components/AddUser"

function App() {

  return (
    <div>
      <Routes>
        <Route index path="" element={<UserList />} />
        <Route index path="/add-user" element={<AddUser />} />
        <Route index path="/users" element={<UserList />} />
      </Routes>
    </div>
  )
}

export default App
