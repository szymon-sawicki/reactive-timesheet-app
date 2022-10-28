import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import NavbarComponent from "./components/NavbarComponent";
import CreateUserComponent from "./components/CreateUserComponent";
import Home from "./components/Home";
import CreateTeamComponent from "./components/CreateTeamComponent";

const App = () => {
    return (
        <Router>
            <NavbarComponent/>
            <Routes>
                <Route exact path='/' element={<Home/>}/>
                <Route path='/user-creation' element={<CreateUserComponent/>}/>
                <Route path='/user-creation' element={<CreateUserComponent/>}/>
                <Route path='/team-creation' element={<CreateTeamComponent/>}/>
            </Routes>
        </Router>
    )
}

export default App;