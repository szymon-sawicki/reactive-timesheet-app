import React from 'react';
import ReactDOM from 'react-dom/client';
import CreateUserComponent from "./components/CreateUserComponent";

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <React.StrictMode>
        <CreateUserComponent/>
    </React.StrictMode>
);

