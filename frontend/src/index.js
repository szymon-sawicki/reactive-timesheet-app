import React from 'react';
import ReactDOM from 'react-dom/client';
import FirstComponent from "./FirstComponent";

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <React.StrictMode>
        <FirstComponent/>
    </React.StrictMode>
);

