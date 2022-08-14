import React, {useEffect, useState} from "react";
import axios from "axios";

const useInput = (initialValue) => {
    const [value, setValue] = useState(initialValue);
    const [touched, setTouched] = useState(false);

    return {
        value,
        setValue,
        touched,
        setTouched,
        reset: () => setValue(''),
        bind: {
            value,
            onChange: (event) => {
                setValue(event.target.value);
                setTouched(true);
            }
        }
    }
}

const SelectGroup = ({items, handleItemsChange, groupName}) => (
    <div>
        <label>{groupName}</label>
        <select className="form-select" onChange={handleItemsChange}>
            {
                items.map(item => <option key={item} value={item}>{item}</option>)
            }
        </select>
    </div>
)

const CreateUserComponent = () => {
    const {
        value: username,
        touched: touchedUsername,
        setTouched: setTouchedUsername,
        bind: bindUsername,
        reset: resetUsername
    } = useInput('');
    const {
        value: password,
        touched: touchedPassword,
        setTouched: setTouchedPassword,
        bind: bindPassword,
        reset: resetPassword
    } = useInput('');
    const {
        value: passwordConfirmation,
        touched: touchedPasswordConfirmation,
        setTouched: setTouchedPasswordConfirmation,
        bind: bindPasswordConfirmation,
        reset: resetPasswordConfirmation
    } = useInput('');

    const roles = ['', 'DEVELOPER', 'LEAD'];
    const [selectedRole, setSelectedRole] = useState('');

    const teams = ['', 'Team 1', 'Team 2', 'Team 3'];  // TODO implement fetching of teams
    const [selectedTeam, setSelectedTeam] = useState('');


    const [error, setError] = useState({
        userName: false,
        password: false,
        passwordConfirmation: false,
        role: false,
        team: false
    })

    const validateTextInput = (value) => value.length > 4;

    const validatePassword = (value) => value.length > 4;      // TODO create better validation

    const validatePasswordConfirmation = (value) => value.length > 4 && value === password;

    const validateTeams = (items) => Object.keys(items).length > 0 && selectedTeam !== '';

    const validateRoles = (items) => Object.keys(items).length > 0 && selectedRole !== '';

    const isValid = () => Object.entries(error).every((x => x[1]));

    useEffect(() => {
        setError({
            username: validateTextInput(username),
            password: validatePassword(password),
            passwordConfirmation: validatePasswordConfirmation(passwordConfirmation),
            role: validateRoles(roles),
            team: validateTeams(teams)
        });
    }, [username, password, roles, teams]);

    const handleSubmit = (event) => {

        event.preventDefault();

        console.log(`Username: ${username}  Password: ${password}  Team: ${selectedTeam}   Role: ${selectedRole}`);

        resetUsername();
        resetPassword();
        resetPasswordConfirmation();
        setSelectedRole('');
        setSelectedTeam('');

        setTouchedUsername(false);
        setTouchedPassword(false);
        setTouchedPasswordConfirmation(false);
        setTouchedPasswordConfirmation(false);
    }

    const handleTeamsChange = (event) => {
        setSelectedTeam(event.target.value);
    }

    const handleRolesChange = (event) => {
        setSelectedRole(event.target.value);
    }

    const useAxiosGet = (url) => {
        const [data, setData] = useState(null);
        const [axiosError, setAxiosError] = useState("");
        const [loaded, setLoaded] = useState(false);

        useEffect(() => {
            axios
                .get(url)
                .then((response) => setData(response.data))
                .catch((error) => setError(error))
                .finally(() => setLoaded(true))
        }, []);
        return [data, error, loaded];
    }

    const teamsFromApi = useAxiosGet("http://localhost:8080/teams");

    return (
        <div className="container">
            <div className="header"><h2>Create user</h2></div>
            <div className="row">
                <div className="col-8 mt-5">
                    <form onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label>Username:</label>
                            <input
                                type="text"
                                className="form-control"
                                {...bindUsername}
                            /><br/>
                            {
                                !error.username && touchedUsername &&
                                <label className="text-danger">Username error</label>
                            }
                        </div>
                        <div className="form-group">
                            <label>Password:</label>
                            <input
                                type="text"
                                className="form-control"
                                {...bindPassword}
                            /><br/>
                            {
                                !error.password && touchedPassword &&
                                <label className="text-danger">Password error</label>
                            }
                        </div>
                        <div className="form-group">
                            <label>PasswordConfirmation:</label>
                            <input
                                type="text"
                                className="form-control"
                                {...bindPasswordConfirmation}
                            /><br/>
                            {
                                !error.passwordConfirmation && touchedPasswordConfirmation &&
                                <label className="text-danger">Password confirmation error</label>
                            }
                        </div>
                        <div className="form-group">
                            {
                                SelectGroup({
                                    items: roles,
                                    handleItemsChange: handleRolesChange,
                                    groupName: 'Roles'
                                })
                            }<br/>
                            {
                                !error.role && <label className="text-danger">Role error</label>
                            }
                            <br/>
                        </div>
                        <div className="form-group">
                            {
                                SelectGroup({
                                    items: teams,
                                    handleItemsChange: handleTeamsChange,
                                    groupName: 'Teams'
                                })
                            }<br/>
                            {
                                !error.team && <label className="text-danger">Team error</label>
                            }
                            <br/>
                        </div>
                        <div className="form-group">
                            <button disabled={!isValid()} type="submit">Send</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default CreateUserComponent;