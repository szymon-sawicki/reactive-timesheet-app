export default class GetUserDto {
    constructor(id, username, role, teamName) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.teamName = teamName;
    }

    toString() {
        return `${this.id} ${this.username} ${this.role} ${this.teamName}`;
    }
}