export default class GetTeamDto {
    constructor(id, name, role, members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }

    toString() {
        return `${this.id} ${this.name} ${this.members}`;
    }
}