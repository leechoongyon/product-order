import http from 'k6/http';

const BASE_URL = 'http://localhost:8080';
const USER_COUNT = 5;
const PRODUCT_IDS = ['product1001', 'product1002', 'product1003', 'product1004', 'product1005'];

export const options = {
  vus: USER_COUNT,
  duration: '20s',
};

export default function () {
  const userId = 'user' + __VU;
  const productId = PRODUCT_IDS[__VU % PRODUCT_IDS.length];
  const quantity = 10;

  const payload = JSON.stringify({
    userId: userId,
    productId: productId,
    quantity: quantity,
  });

  const url = `${BASE_URL}/api/v1/orders`;

  const headers = { 'Content-Type': 'application/json' };
  const response = http.post(url, payload, { headers: headers });

  if (response.status !== 200) {
    console.error(`Error for ${userId} - ${productId}: ${response.status} - ${response.body}`);
  }
}
