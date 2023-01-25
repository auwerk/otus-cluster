import { check, fail, sleep } from 'k6';
import http from 'k6/http';
import exec from 'k6/execution';

const KUBE_HOST = "arch.homework:30043";

function initUserData(userNum) {
	return {
        username: `user${userNum}`,
        email: `user${userNum}@users.ru`,
        firstName: 'John',
        lastName: 'Doe',
        phone: 99999
    };
}

function createUser(userNum) {
    const user = initUserData(userNum);

    const params = {
        headers: {
            'Content-Type': 'application/json'
        },
    };

    const res = http.post(`http://${KUBE_HOST}/user/`, JSON.stringify(user), params);

    const checkResponse = check(res, { 'createUser.status is 200': (r) => r.status == 200 });
    if (!checkResponse) {
        fail(`unexpected response: status=${res.status}, body=${res.body}`);
    }

    return res.json().id;
}

function duplicateUser(userNum) {
	const user = initUserData(userNum);

    const params = {
        headers: {
            'Content-Type': 'application/json'
        },
    };

    const res = http.post(`http://${KUBE_HOST}/user/`, JSON.stringify(user), params);
	check(res, { 'duplicateUser.status is 500': (r) => r.status == 500 });
}

function deleteUser(userId) {
    const res = http.del(`http://${KUBE_HOST}/user/${userId}`);
    check(res, { 'deleteUser.status is 204': (r) => r.status == 204 });
}

function getUserById(userId) {
	const res = http.get(`http://${KUBE_HOST}/user/${userId}`);
	check(res, { 'getUserById.status is 200': (r) => r.status == 200 });
}

function getUsers() {
	const res = http.get(`http://${KUBE_HOST}/user/`);
	check(res, { 'getUsers.status is 200': (r) => r.status == 200 });
}

export default function() {
    const userNum = exec.vu.idInTest;

    let userId = createUser(userNum);
    sleep(1);
	let duplicateChance = Math.random();
	if (duplicateChance > 0.8) {
		duplicateUser(userNum);
		getUsers();
	}
	getUserById(userId);
    deleteUser(userId);
}
