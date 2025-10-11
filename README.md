# Patient Management System

A production-ready, enterprise-level patient management system built with microservices architecture, demonstrating modern cloud-native technologies and best practices. This system showcases end-to-end development from local development to cloud deployment using simulated AWS services.

## 🏗️ Architecture Overview

The system follows a **microservices architecture** with the following key components:

### Core Services

| Service | Port | Purpose | Technology Stack |
|---------|------|---------|------------------|
| **API Gateway** | 4004 | Single entry point, routing, JWT validation | Spring Cloud Gateway |
| **Auth Service** | 4005 | Authentication & authorization, JWT token management | Spring Boot Security |
| **Patient Service** | 4000 | Patient CRUD operations, communication hub | Spring Boot, JPA |
| **Billing Service** | 4001, 9001 | Billing account management | Spring Boot, gRPC |
| **Analytics Service** | 4002 | Patient analytics and reporting | Spring Boot, Kafka Consumer |

### Communication Patterns

- **REST API**: Client-to-service communication via API Gateway
- **gRPC**: High-performance service-to-service communication (Patient → Billing)
- **Kafka**: Asynchronous event-driven communication for analytics and notifications

## 🚀 Quick Start

### Prerequisites

- **Java 21** (LTS version)
- **Docker Desktop** (for containerization)
- **Maven 3.9+** (dependency management)
- **AWS CLI** (for LocalStack deployment)
- **IntelliJ IDEA Ultimate** (recommended IDE)

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd patient-management
   ```

2. **Install and Start LocalStack (AWS simulation)**

   **Option 1: Using Docker (Recommended)**
   ```bash
   # Install LocalStack using Docker
   docker run --rm -it -p 4566:4566 -p 4571:4571 localstack/localstack
   ```

   **Option 2: Using pip**
   ```bash
   # Install LocalStack via pip
   pip install localstack
   
   # Start LocalStack
   localstack start
   ```

   **Option 3: Using Docker Compose**
   ```yaml
   # Create docker-compose.yml
   version: '3.8'
   services:
     localstack:
       container_name: localstack
       image: localstack/localstack
       ports:
         - "4566:4566"
         - "4571:4571"
       environment:
         - SERVICES=ecs,ec2,cloudformation,iam,sts,elbv2,rds,kafka
         - DEBUG=1
         - DATA_DIR=/tmp/localstack/data
       volumes:
         - "/var/run/docker.sock:/var/run/docker.sock"
   ```
   ```bash
   # Start with Docker Compose
   docker-compose up
   ```

3. **Deploy Infrastructure**
   ```bash
   cd infrastructure
   ./localstack-deploy.sh
   ```

4. **Build and Deploy Services**
   ```bash
   # Build all services
   mvn clean package -DskipTests
   
   # Build Docker images
   docker build -t auth-service ./auth-service
   docker build -t patient-service ./patient-service
   docker build -t billing-service ./billing-service
   docker build -t analytics-service ./analytics-service
   docker build -t api-gateway ./api-gateway
   ```

5. **Get Load Balancer Endpoint**
   ```bash
   aws --endpoint-url=http://localhost:4566 elbv2 describe-load-balancers \
       --query "LoadBalancers[0].DNSName" --output text
   ```

## 📡 API Endpoints

### Authentication

| Method | Endpoint | Description | Authentication |
|--------|----------|-------------|----------------|
| POST | `/auth/login` | User login | None |
| POST | `/auth/validate` | Validate JWT token | None |

### Patient Management

| Method | Endpoint | Description | Authentication |
|--------|----------|-------------|----------------|
| GET | `/api/patients` | Get all patients | JWT Required |
| POST | `/api/patients` | Create new patient | JWT Required |
| PUT | `/api/patients/{id}` | Update patient | JWT Required |
| DELETE | `/api/patients/{id}` | Delete patient | JWT Required |

### API Documentation

- **Patient Service API Docs**: `http://<load-balancer-endpoint>:4004/api-docs/patients`
- **Auth Service API Docs**: `http://<load-balancer-endpoint>:4004/api-docs/auth`

## 🧪 Testing

### Integration Tests

Run the comprehensive integration test suite:

```bash
cd integration-tests
mvn test
```

The test suite includes:
- Authentication flow testing
- Patient CRUD operations
- End-to-end API Gateway routing
- JWT token validation

### Manual Testing

Use the provided HTTP request files in the `api-requests/` directory:

```bash
# Example: Login and get token
POST http://<load-balancer-endpoint>:4004/auth/login
Content-Type: application/json

{
  "email": "testuser@test.com",
  "password": "password123"
}
```

## 🏛️ Infrastructure as Code

The system uses **AWS CDK** to define infrastructure as code:

### AWS Resources (Simulated via LocalStack)

- **VPC**: Network isolation and security
- **ECS Cluster**: Container orchestration
- **RDS**: Managed PostgreSQL databases
- **MSK**: Managed Kafka cluster
- **ALB**: Application Load Balancer for external access

### Deployment Commands

```bash
# Deploy infrastructure
cd infrastructure
./localstack-deploy.sh

# Get deployment status
aws --endpoint-url=http://localhost:4566 cloudformation describe-stacks \
    --stack-name patient-management
```

## 🔧 Technology Stack

### Backend & Core
- **Java 21** - Core development language
- **Spring Boot 3.x** - Microservices framework
- **Spring Data JPA** - Database persistence layer
- **Spring Security** - Authentication and authorization

### Databases
- **PostgreSQL** - Production database
- **H2 Database** - Local development and testing

### Communication
- **REST API** - Client communication
- **gRPC** - High-performance service-to-service communication
- **Apache Kafka** - Asynchronous event streaming
- **Protocol Buffers** - Efficient data serialization

### Containerization & Orchestration
- **Docker** - Containerization
- **AWS ECS** - Container orchestration
- **AWS Fargate** - Serverless container execution

### Infrastructure & Deployment
- **AWS CDK** - Infrastructure as Code
- **CloudFormation** - AWS resource provisioning
- **LocalStack** - Local AWS service simulation

### Development Tools
- **Maven** - Dependency management
- **OpenAPI/Swagger** - API documentation
- **Rest Assured** - Integration testing
- **IntelliJ IDEA** - Development IDE

## 📊 System Flow

### Patient Creation Flow

1. **Client Request** → API Gateway (Port 4004)
2. **Authentication** → Auth Service validates JWT
3. **Patient Creation** → Patient Service (Port 4000)
4. **Business Logic** → Email uniqueness validation
5. **Database Persistence** → PostgreSQL via JPA
6. **Synchronous Communication** → Billing Service via gRPC
7. **Asynchronous Event** → Analytics Service via Kafka
8. **Response** → Client receives patient data

### Authentication Flow

1. **Login Request** → API Gateway
2. **Credential Validation** → Auth Service
3. **JWT Generation** → Secure token creation
4. **Token Response** → Client receives JWT
5. **Protected Requests** → JWT validation via API Gateway

## 🗂️ Project Structure

```
patient-management/
├── api-gateway/              # API Gateway service
├── auth-service/             # Authentication service
├── patient-service/          # Patient management service
├── billing-service/          # Billing service
├── analytics-service/        # Analytics service
├── infrastructure/           # AWS CDK infrastructure code
├── integration-tests/        # End-to-end tests
├── api-requests/            # HTTP request examples
├── grpc-requests/           # gRPC request examples
└── README.md               # This file
```

## 🔐 Security Features

- **JWT-based Authentication**: Secure token-based authentication
- **Password Encryption**: BCrypt password hashing
- **API Gateway Security**: Centralized authentication and authorization
- **Network Isolation**: VPC-based network security
- **Input Validation**: Comprehensive request validation

## 📈 Scalability Features

- **Microservices Architecture**: Independent service scaling
- **Container Orchestration**: ECS-based container management
- **Load Balancing**: ALB for traffic distribution
- **Event-Driven Architecture**: Kafka for asynchronous processing
- **Database Optimization**: JPA with connection pooling

## 🚀 Production Deployment

### AWS Services Used

- **VPC**: Network isolation
- **ECS**: Container orchestration
- **RDS**: Managed databases
- **MSK**: Managed Kafka
- **ALB**: Load balancing
- **CloudFormation**: Infrastructure provisioning

### Deployment Steps

1. **Build Docker Images**
2. **Deploy Infrastructure** (CDK → CloudFormation)
3. **Deploy Services** (ECS Fargate)
4. **Configure Load Balancer**
5. **Validate Deployment**

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request


## 🆘 Support

For questions and support:
- Create an issue in the repository
- Check the API documentation endpoints
- Review the integration test examples

---

## 📺 Tutorial Credits

I have referred this project from the following YouTube Video by [@chrisblakely01](https://github.com/chrisblakely01):

**[Patient Management System - Complete Walkthrough](https://youtu.be/tseqdcFfTUY?list=TLGGwo0SSwT-uC8xMTEwMjAyNQ)**

The video covers:
- Complete system architecture overview
- Step-by-step local development setup
- LocalStack installation and configuration
- Infrastructure deployment using AWS CDK
- Microservices communication patterns
- Integration testing and validation
- Production deployment considerations