import React, {useEffect, useRef, useState} from "react";

/*
    Funkcja zarzadza generowaniem kluczy
    Kazde dodanie lub usuniecie klucza z tablicy spowoduje przerenderowanie komponentow i
    wygenerowanie formularza z kolejnymi kontaktami
*/

const useKeys = (init) => {
    // Generowanie tablicy kluczy
    let [keys, setKeys] = useState(Array(init).fill(0).map((value, pos) => pos + 1));

    const add = () => setKeys(keys.concat(keys.length + 1))

    const remove = (key) => {
        let newKeys = [...keys];
        let index = newKeys.indexOf(key);
        if (index !== -1) {
            newKeys.splice(index, 1);
        }
        setKeys(newKeys);
    }

    return [keys, add, remove];
}

const Contact = ({validation, setValidation, dataToSend: data, position, onRemove}) => {
    let [name, setName] = useState('');
    let [email, setEmail] = useState('');
    let [errors, setErrors] = useState([]);

    // useEffect wykorzystamy do walidaowania
    useEffect(() => {
        let errorMessages = [];

        // Walidacja pola name

        if (!name || name.length < 3) {
            errorMessages.push('Name is not correct');
            data[position].name = '';
        } else {
            data[position].name = name;
        }

        // Walidacja pola email
        if (!email || email.length < 3) {
            errorMessages.push('Email is not correct');
        } else {
            data[position].email = email;
        }

        // wysylamy info do stanu komponentu nadrzednego
        setValidation({...validation, [position]: errorMessages.length === 0})

        // Tutaj zmieniamy stan errors, zeby przechwycic bledy, ktore przytrafily sie do konkretnego
        // walidowanego kontaktu
        setErrors(errorMessages);

    }, [name, email]);

    return (
        <div className="border p-2 my-3">
            <div className="form-group">
                <label>Name</label>
                <input
                    value={name}
                    onChange={e => setName(e.target.value)}
                    className="form-control"
                />
            </div>
            <div className="form-group">
                <label>Email</label>
                <input
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    className="form-control"
                />
            </div>
            <div className="form-group">
                <button onClick={() => onRemove(position)} className="mb-3">Remove</button>
                {
                    <div className="text-danger">
                        {errors.map(err => <h6 key={err}>{err}</h6>)}
                    </div>
                }
            </div>
        </div>
    );
}

const SixthComponentDynamicForm = () => {

    // TODO Zrobic opis do tego

    const [keys, add, remove] = useKeys(2);

    // Przygotowanie stanu, ktory nie bedzie powodowal renderowania, a bedzie
    // przechowywal dane z pol name oraz email dla kazdego wygenerowanego komponentu Contact
    const dataToSend = useRef(keys.reduce((acc, value) => {
        acc[value] = {};
        return acc;
    }, {}));

    const [validation, setValidation] = useState(keys.reduce((accum, value) => {
        accum[value] = false;
        return accum;
    }, {}))

    let contacts = keys
        .filter(key => key in dataToSend.current)
        .map(key => (
            <Contact
                key={key}
                validation={validation}
                setValidation={setValidation}
                dataToSend={dataToSend.current}
                position={key}
                onRemove={remove}
            />
        ))

    const send = () => {
        console.log(dataToSend.current);
    }

    useEffect(() => {
        setValidation(keys.reduce((accum, value) => {
            accum[value] = value in validation ? validation[value] : false;
            return accum;
        }, {}))

        dataToSend.current = keys.reduce((accum, value) => {
            accum[value] = value in dataToSend.current ? dataToSend.current[value] : {name: '', email: ''}
            return accum;
        }, {})

        contacts = keys.map(key => (
            <Contact
                key={key}
                validation={validation}
                setValidation={setValidation}
                dataToSend={dataToSend.current}
                position={key}
                onRemove={remove}
            />
        ))
    }, [keys])

    return (
        <div>
            {contacts}
            <button onClick={add} className="btn btn-primary mr-2">Add</button>
            <button
                disabled={Object.entries(validation).length === 0 || !Object.entries(validation).map(e => e[1]).every(x => x)}
                onClick={send}
                className="btn btn-secondary"
            >Send
            </button>
        </div>
    );
}

export default SixthComponentDynamicForm;
