import axios from 'axios'

// axios base구조 잡기 위한 파일!
const DEFAULT_ACCEPT_TYPE = 'application/json'
export default axios.create({
  baseURL:"https://i5b105.p.ssafy.io:8080/api", 
  headers:{'Content-Type': DEFAULT_ACCEPT_TYPE,
            'Access-Control-Allow-Origin':'*',
          }
});

