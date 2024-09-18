const BASE_URL = "/api";
const PI = 3.14;

const getDepartments = axios.get(BASE_URL + '/department/list')

export {BASE_URL, PI};
