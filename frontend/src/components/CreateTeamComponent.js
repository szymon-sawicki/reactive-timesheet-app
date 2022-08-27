import React, {useEffect, useState} from "react";

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

const useKeys = (init) => {

    let [keys, setKeys] = useState(Array(init).fill(0).map((value, pos) => pos + 1));

    const add = () => setKeys(keys.concat(keys.length + 1));

    const remove = (key) => {
        let newKeys = [...keys];
        let index = newKeys.indexOf(key);
        if (index !== -1) {
            newKeys.splice(index, 1);
        }
        setKeys(newKeys);

        return [keys, add, remove];
    }

    const User = ({validation, setValidation, dataToSend: data, position, onRemove}) => {


        const roles = ['', 'DEVELOPER', 'LEAD'];
        const [selectedRole, setSelectedRole] = useState('');
        let [errors, setErrors] = useState([]);

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

        useEffect(() => {
            let errorMessages = [];

            if (username.length < 4) {
                errorMessages.push('username have wrong format');
                data[position].username = '';
            } else {
                data[position].username = '';
            }

            if (password.length < 4) {
                errorMessages.push('password have wrong format');
            } else {
                data[position].password = '';
            }

            setValidation({...validation, [position]: errorMessages.length === 0});

            setErrors(errorMessages);

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
                                    />
                                </div>
                                <div className="form-group">
                                    <label>Password:</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        {...bindPassword}
                                    />
                                </div>
                                <div className="form-group">
                                    {
                                        SelectGroup({
                                            items: roles,
                                            handleItemsChange: handleRolesChange,
                                            groupName: 'Roles'
                                        })
                                    }
                                </div>
                                <div className="form-group">
                                    <button onClick={() => onRemove(position)} className="mb-3">Remove</button>
                                    {
                                        <div className="text-danger">
                                            {errors.map(err => <h6 key={err}>{err}</h6>)}
                                        </div>
                                    }
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            )
        })
    }
}