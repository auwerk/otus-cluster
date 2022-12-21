import { check, fail, sleep } from 'k6';
import http from 'k6/http';
import exec from 'k6/execution';

function createUser(userNum) {
    const user = {
        username: `user${userNum}`,
        email: `user${userNum}@users.ru`,
        firstName: 'John',
        lastName: 'Doe',
        phone: 99999
    };

    const params = {
        headers: {
            'Content-Type': 'application/json'
        },
    };

    const res = http.post('http://arch.homework/user/', JSON.stringify(user), params);

    const checkResponse = check(res, { 'createUser.status is 200': (r) => r.status == 200 });
    if (!checkResponse) {
        fail(`unexpected response: status=${res.status}, body=${res.body}`);
    }

    return res.json().id;
}

function deleteUser(userId) {
    const res = http.del(`http://arch.homework/user/${userId}`);
    check(res, { 'deleteUser.status is 204': (r) => r.status == 204 });
}

export default function() {
    const userNum = exec.vu.idInTest;

    let userId = createUser(userNum);
    sleep(1);
    deleteUser(userId);
}
