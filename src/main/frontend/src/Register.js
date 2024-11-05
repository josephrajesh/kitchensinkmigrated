import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

const Register = ({updateMembers}) => {
    const handleSubmit = (event) => {
        event.preventDefault();
        const fd = new FormData(event.target);
        const data = Object.fromEntries(fd.entries());

        console.log(data);
        sunbmitForm(data)

    };

    const sunbmitForm = async (data) => {
        const response = await fetch('http://localhost:8081/kitchensinknew/rest/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const result = await response.json();
        updateMembers(result);
    }

    return (
        <>
            <Row className="align-items-center">
                <Col md="auto">
                    <h5>Member Registration </h5>
                    Enforces annotation-based constraints defined on the model class.
                </Col>
                <Col xs lg="6" justify="left">
                    <h6>Learn more about MongoDB's Application Modernization.</h6>
                </Col>
            </Row>
            <div className="line"></div>
            <br />
            <br />
            <Row md="auto">
                <form onSubmit={handleSubmit} >
                    <Row md="auto">
                        <Col xs lg="3">
                            <label htmlFor="name"> Name: </label>
                        </Col>
                        <Col md="auto" justify="central">
                            <input type="text" name="name" required />
                        </Col>
                    </Row>
                    <br />
                    <Row md="auto">
                        <Col xs lg="3">
                            <label htmlFor="email"> Email: </label>
                        </Col>
                        <Col md="auto" justify="central">
                            <input type="email" htmlFor="email" name="email" required />
                        </Col>
                    </Row>
                    <br />
                    <Row md="auto">
                        <Col xs lg="3">
                            <label htmlFor="phoneNumber"> Phone: </label>
                        </Col>
                        <Col md="auto" justify="central">
                            <input type="text" name="phoneNumber" required />
                        </Col>
                    </Row>
                    <br />
                    <br />
                    <button className="button">Register</button>
                </form>
            </Row>
        </>
    );
}

export default Register