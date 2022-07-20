import React from "react";
import CreateUserDto from "../model/CreateUserDto";

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

         // TODO

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