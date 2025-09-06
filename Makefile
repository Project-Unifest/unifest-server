deploy-unifest:
	git pull origin main
	docker build -t unifest-prod .
	docker stop unifest-prod || true
	docker rm unifest-prod || true
	docker run -d --name unifest-prod -p 8080:8080 --env-file .env.local unifest-prod
