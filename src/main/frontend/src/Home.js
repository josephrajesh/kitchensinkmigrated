import Header from './Header';
import Members from './Members';
import Register from './Register';
import { useState, useEffect } from 'react';



const Home = () => {
  const [members, setMembers] = useState([]);
  const [loading, setLoading] = useState(false)
  useEffect(() => {
    async function fetchMembers() {
      setLoading(true);
      const response = await fetch('http://localhost:8081/kitchensinknew/rest/members');
      const data = await response.json();
      setMembers(data);
      setLoading(false);
    }
    fetchMembers();
  }, []);
  return (
    <>
      <Header />
      <br />
      <br />
      <Register updateMembers={setMembers} />
      <br />
      <br />
      {loading && <p>Loading...</p>}
      {!loading && members && <Members members={members} />}

    </>
  )
}

export default Home;