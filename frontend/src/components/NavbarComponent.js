import React from 'react';

const NavbarComponent = () => (
    <div>
        <nav className="navbar navbar-expand-md navbar-dark bg-dark mb-4">
            <a className="navbar-brand" href="#">Reactive timesheet app</a>
            <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse"
                    aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarCollapse">
                <ul className="navbar-nav mr-auto">
                    <li className="nav-item active">
                        <a className="nav-link" href="/">Home <span className="sr-only">(current)</span></a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="/user-creation">Create user</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link disabled" href="/team-creation">Create team</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link disabled" href="/users">All users</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link disabled" href="/teams">All teams</a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
)

export default NavbarComponent;