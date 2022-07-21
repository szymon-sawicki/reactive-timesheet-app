import React from "react";

class CreateUserComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            passwordConfirmation: '',
            role: '',
            teamName: '',

            usernameErrorMessage: '',
            isUsernameError: '',

            passwordErrorMessage: '',
            isPasswordError: '',

            usernameTouched: false,
            passwordTouched: false,
            passwordConfirmationTouched: false,
            roleTouched: false,
            teamNameTouched: false,

            isFormValid: true
        }
    }

    handleChange = (event) => {

        const {target} = event;
        let value = target.value;
        const {name} = target;

        if (name === 'username') {
            if (value.length < 5) {
                this.setState({
                    usernameErrorMessage: 'Name should have at least 5 characters',
                    isNameError: true,
                    isFormValid: false
                })
            } else {
                this.setState({
                    usernameErrorMessage: '',
                    isUsernameError: false
                })
            }
        }

        if (name === 'password') {
            const passwordRegex = "^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$";

            if (!value.test(passwordRegex)) {
                this.setState({
                    passwordErrorMessage: 'password does not matches the policy',
                    isPasswordError: true,
                    isFormValid: false
                })
            }
        }

        if (name === 'passwordConfirmation') {
            if (value !== this.state.password) {
                this.setState({
                    passwordErrorMessage: 'password and confirmation does not matches',
                    isPasswordError: true,
                    isFormValid: false
                })
            }
        }

        this.setState({[name]: value});
        this.setState({[`${nameTouched}`]: true});
    }

    clearFields = () => {
        this.setState({
            username: '',
            password: '',
            passwordConfirmation: '',
            role: '',
            teamName: '',

            usernameTouched: false,
            passwordTouched: false,
            passwordConfirmationTouched: false,
            roleTouched: false,
            teamNameTouched: false
        })
    }
}