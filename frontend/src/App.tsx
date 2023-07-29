import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Stomp, Client } from '@stomp/stompjs';
import 'bootstrap/dist/css/bootstrap.css';
import { Button, Col, ListGroup, Row, Table } from 'react-bootstrap';
import { Container } from 'react-bootstrap';

function App() {

  class Order {
    constructor(
      public orderId: string,
      public productName: string,
      public price: number) { }
  }

  class Notification {
    constructor(
      public orderId: string,
      public message: string) { }
  }

  const [orders, setOrders] = React.useState<Order[]>([])
  const [notificationsInQueue, setNotificationsInQueue] = React.useState<Notification[]>([])
  const [notificationsProcessed, setNotificationsProcessed] = React.useState<Notification[]>([])

  var client = new Client();
  client.configure({
    brokerURL: 'ws://localhost:8080/socket',
    onConnect: () => {

      client.subscribe('/topic/producer/notification', message => {
        var notification: Notification = JSON.parse(message.body) as Notification
        setNotificationsInQueue([
          ...notificationsInQueue, notification
        ])
      })

      client.subscribe('/topic/producer/order', message => {
        var order: Order = JSON.parse(message.body) as Order
        setOrders([
          ...orders, order
        ])
      })

      client.subscribe('/topic/consumer', message => {
        var notification: Notification = JSON.parse(message.body) as Notification
        setNotificationsProcessed([
          ...notificationsProcessed, notification
        ])
        setNotificationsInQueue(notificationsInQueue.filter(n => n.orderId == notification.orderId))
      })

    },
    onWebSocketError: (err) => console.log(err)
  })
  client.activate()

  return (
    <div className="App">
      <div className='mt-3'>
        <h1>
          RabbitMQ Visualizer
        </h1>
        <hr></hr>
      </div>

      <Container>
        <Row>
          <Col>
            <div className='d-flex justify-content-center mt-5 pt-5'>
              <div className="justify-content-center align-items-center 
              h-100 w-100 bg-light align-middle border rounded shadow p-3" id="producer">
                Orders Created
                <ListGroup>
                  <Table striped bordered hover>
                    <thead>
                      <tr>
                        <th>OrderId</th>
                        <th>Product</th>
                        <th>Price</th>
                      </tr>
                    </thead>
                    <tbody>
                      {orders.map((e) => {
                        return <tr>
                          <td>{e.orderId}</td>
                          <td>{e.productName}</td>
                          <td>{e.price}</td>
                        </tr>
                      })}
                    </tbody>
                  </Table>
                </ListGroup>
              </div>
            </div>
          </Col>
          <Col>
            <div className='d-flex justify-content-center mt-5 pt-5'>
              <div className="justify-content-center align-items-center
               h-100 w-100 bg-light align-middle border rounded shadow p-3" id="consumer">
                Notifications in Queue
                <ListGroup>
                  {notificationsInQueue.map((e) => {
                    return <ListGroup.Item className='d-flex justify-content-between'>
                      <div>
                        {/* OrderId: {e.orderId} */}
                        {/* <br></br> */}
                        Message: {e.message}
                        {/* <br></br> */}
                        {/* Price: {e.dateTime.toString()} */}
                      </div>
                    </ListGroup.Item>
                  })}
                </ListGroup>
              </div>
            </div>
          </Col>
          <Col>
            <div className='d-flex justify-content-center mt-5 pt-5'>
              <div className="justify-content-center align-items-center
               h-100 w-100 bg-light align-middle border rounded shadow p-3" id="consumer">
                Notifications sent
                <ListGroup>
                  {notificationsProcessed.map((e) => {
                    return <ListGroup.Item className='d-flex justify-content-between'>
                      <div>
                        {/* OrderId: {e.orderId} */}
                        {/* <br></br> */}
                        Message: {e.message}
                        {/* <br></br> */}
                        {/* Price: {e.dateTime.toString()} */}
                      </div>
                    </ListGroup.Item>
                  })}
                </ListGroup>
              </div>
            </div>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default App;
