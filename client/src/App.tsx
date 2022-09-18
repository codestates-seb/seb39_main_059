import logo from './logo.svg';
import './App.css';

function App() {
  console.log(process.env.PUBLIC_URL)
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>{'NODE_ENV= '}{process.env.NODE_ENV}</p>
        <p>{'PUBLIC_URL= '}{process.env.PUBLIC_URL}</p>
        <p>{'IMAGE_INLINE_SIZE_LIMIT= '}{process.env.IMAGE_INLINE_SIZE_LIMIT}</p>
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
