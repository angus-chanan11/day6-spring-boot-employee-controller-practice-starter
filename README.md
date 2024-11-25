# GET #obtain company list with response of id, name
method: GET
url: /companies
status code: 200
response: [company objects]

# GET #obtain a certain specific company with response of id, name
method: GET
url: /companies/{id}
status code: 200
response: {company object}

# GET #obtain list of all employee under a certain specific company
method: GET
url: /companies/{id}/employees
status code: 200
response: [employee objects]

# GET #Page query, page equals 1, size equals 5, it will return the data in company list from index 0 to index 4.
method: GET
url: /companies?page=1&size=5
status code: 200
response: [company objects]

# PUT # update an employee with company
method: PUT
url: /companies/{id}/employees/{employeeId}
body: {id: int, name: string, age: int, gender: string, salary: double}
status code: 200
response: {employee object}

# POST #add a company
method: POST
url: /companies
body: {id: int, name: string}
status code: 201
response: {company object}

# DELETE # delete a company
method: DELETE
url: /companies/{id}
status code: 204