export default class CreateUserDto {
    constructor(username, password, passwordConfirmation, role, teamName) {
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.role = role;
        this.teamName = teamName;
    }
}