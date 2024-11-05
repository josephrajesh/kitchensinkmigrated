import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { useState } from 'react';
import { useForm } from 'react-hook-form';

const Register = ({updateMembers}) => {
  const [error, setError] = useState(null);
  const { register, handleSubmit, reset } = useForm();

    const onSubmit = handleSubmit((data) => {
        setError(null);
        console.log(data);
        submitForm(data);
    });

    const submitForm = async (data) => {
        try{
            const response = await fetch('http://localhost:8081/kitchensinknew/rest/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const result = await response.json();
            reset();
            updateMembers(result);
        } catch (error) {
            console.error('Error: ', error.message, error);
            setError(error.message);
        }
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
                <form onSubmit={handleSubmit(onSubmit)} >
                    <Row md="auto">
                        <Col xs lg="3">
                            <label htmlFor="name"> Name: </label>
                        </Col>
                        <Col md="auto" justify="central">
                            <input type="text" name="name" required {...register("name")} />
                        </Col>
                    </Row>
                    <br />
                    <Row md="auto">
                        <Col xs lg="3">
                            <label htmlFor="email"> Email: </label>
                        </Col>
                        <Col md="auto" justify="central">
                            <input type="email" htmlFor="email" name="email" required required {...register("email")} />
                        </Col>
                    </Row>
                    <br />
                    <Row md="auto">
                        <Col xs lg="3">
                            <label htmlFor="phoneNumber"> Phone: </label>
                        </Col>
                        <Col md="auto" justify="central">
                            <input type="text" name="phoneNumber" required required {...register("phoneNumber")} />
                        </Col>
                    </Row>
                    <br />
                    <br />
                    <button className="button">Register</button>
                </form>
            </Row>
            <Row md="auto">
              {error && <p>Error: {error}</p>}
            </Row>
        </>
    );
}

export default Register