# Hackathon Evaluation System - API Documentation

## Base URL
```
http://localhost:8080
```

## Authentication
All endpoints except `/api/auth/login` require JWT authentication.
Include the token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## API Endpoints

### 1. Authentication

#### POST /api/auth/login
Login to get JWT token.

**Request:**
```json
{
    "username": "admin",
    "password": "admin123"
}
```

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Login successful",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY4MDc5NjQwMCwiZXhwIjoxNjgwODgyODAwfQ.signature",
        "type": "Bearer",
        "id": 1,
        "username": "admin"
    }
}
```

**Response (401 Unauthorized):**
```json
{
    "success": false,
    "message": "Invalid username or password",
    "data": null
}
```

### 2. Teams Management

#### POST /api/teams
Create a new team.

**Request:**
```json
{
    "teamName": "Team Alpha"
}
```

**Response (201 Created):**
```json
{
    "success": true,
    "message": "Team created successfully",
    "data": {
        "id": 1,
        "teamName": "Team Alpha",
        "createdAt": "2024-04-06T10:30:00",
        "members": []
    }
}
```

#### GET /api/teams
Get all teams.

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Operation successful",
    "data": [
        {
            "id": 1,
            "teamName": "Team Alpha",
            "createdAt": "2024-04-06T10:30:00",
            "members": [
                {
                    "id": 1,
                    "memberName": "John Doe",
                    "teamId": 1
                }
            ]
        }
    ]
}
```

#### GET /api/teams/{id}
Get team by ID.

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Operation successful",
    "data": {
        "id": 1,
        "teamName": "Team Alpha",
        "createdAt": "2024-04-06T10:30:00",
        "members": []
    }
}
```

#### DELETE /api/teams/{id}
Delete a team.

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Team deleted successfully",
    "data": null
}
```

#### GET /api/teams/search?name=alpha
Search teams by name.

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Operation successful",
    "data": [
        {
            "id": 1,
            "teamName": "Team Alpha",
            "createdAt": "2024-04-06T10:30:00",
            "members": []
        }
    ]
}
```

### 3. Team Members Management

#### POST /api/teams/{teamId}/members
Add a member to a team.

**Request:**
```json
{
    "memberName": "Jane Smith"
}
```

**Response (201 Created):**
```json
{
    "success": true,
    "message": "Member added successfully",
    "data": {
        "id": 2,
        "memberName": "Jane Smith",
        "teamId": 1
    }
}
```

#### DELETE /api/members/{memberId}
Remove a team member.

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Member removed successfully",
    "data": null
}
```

### 4. Sprints Management

#### GET /api/sprints
Get all sprints.

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Operation successful",
    "data": [
        {
            "id": 1,
            "sprintNumber": 0
        },
        {
            "id": 2,
            "sprintNumber": 1
        },
        {
            "id": 3,
            "sprintNumber": 2
        }
    ]
}
```

#### GET /api/sprints/{id}
Get sprint by ID.

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Operation successful",
    "data": {
        "id": 1,
        "sprintNumber": 0
    }
}
```

### 5. Scores Management

#### POST /api/scores
Create or update team score for a sprint.

**Request:**
```json
{
    "teamId": 1,
    "sprintId": 1,
    "marks": 25
}
```

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Score saved successfully",
    "data": {
        "id": 1,
        "team": {
            "id": 1,
            "teamName": "Team Alpha"
        },
        "sprint": {
            "id": 1,
            "sprintNumber": 0
        },
        "marks": 25
    }
}
```

#### GET /api/scores/team/{teamId}
Get all scores for a team.

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Operation successful",
    "data": [
        {
            "id": 1,
            "team": {
                "id": 1,
                "teamName": "Team Alpha"
            },
            "sprint": {
                "id": 1,
                "sprintNumber": 0
            },
            "marks": 25
        }
    ]
}
```

#### GET /api/scores/sprint/{sprintId}
Get all scores for a sprint.

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Operation successful",
    "data": [
        {
            "id": 1,
            "team": {
                "id": 1,
                "teamName": "Team Alpha"
            },
            "sprint": {
                "id": 1,
                "sprintNumber": 0
            },
            "marks": 25
        }
    ]
}
```

### 6. Leaderboard

#### GET /api/leaderboard
Get leaderboard with team rankings.

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Operation successful",
    "data": [
        {
            "teamName": "Team Alpha",
            "totalMarks": 75,
            "status": "Qualified"
        },
        {
            "teamName": "Team Beta",
            "totalMarks": 60,
            "status": "Pending"
        },
        {
            "teamName": "Team Gamma",
            "totalMarks": 45,
            "status": "Rejected"
        }
    ]
}
```

## Error Responses

### Validation Error (400 Bad Request)
```json
{
    "success": false,
    "message": "Validation failed",
    "data": {
        "teamName": "Team name is required",
        "memberName": "Member name must not exceed 100 characters"
    }
}
```

### Resource Not Found (404 Not Found)
```json
{
    "success": false,
    "message": "Team not found with id: 999",
    "data": null
}
```

### Business Logic Error (400 Bad Request)
```json
{
    "success": false,
    "message": "Team cannot have more than 4 members",
    "data": null
}
```

### Unauthorized (401 Unauthorized)
```json
{
    "success": false,
    "message": "Invalid username or password",
    "data": null
}
```

### Internal Server Error (500 Internal Server Error)
```json
{
    "success": false,
    "message": "An unexpected error occurred: Database connection failed",
    "data": null
}
```

## Default Credentials
- **Username:** admin
- **Password:** admin123

## Database Setup
1. Create MySQL database named `hackathon_system`
2. Update `application.properties` with your MySQL credentials
3. Run the application - it will automatically create tables and default data

## Business Rules
- Maximum 4 members per team
- Team names must be unique
- Each team can have only one score per sprint
- Leaderboard status:
  - >= 70 marks: Qualified
  - 50-69 marks: Pending
  - < 50 marks: Rejected
- Scores are calculated dynamically (not stored in database)
