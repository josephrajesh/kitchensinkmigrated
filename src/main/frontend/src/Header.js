import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';


const Header = () => {
  return (
    <>
      <Row className="align-items-center" >
        <Col md="auto" justify="central">Welcome You have successfully modernized kitchen sink using springboot and MongoDB</Col>
        <Col xs lg="2" justify="central">
          <a href="https://www.mongodb.com" className="css-15nzs5q">
           <img src="https://webimages.mongodb.com/_com_assets/cms/kuyjf3vea2hg34taa-horizontal_default_slate_blue.svg?auto=format%252Ccompress" 
           alt="MongoDB logo" width="126" height="120" />
          </a>
        </Col>
      </Row>
    </>
  )
}

export default Header;