package com.connectedgames.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return """
            <!DOCTYPE html>
            <html lang="it">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Connected Games Platform - Central Server</title>
                <style>
                    * { margin: 0; padding: 0; box-sizing: border-box; }
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        min-height: 100vh;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                    }
                    .container {
                        background: white;
                        padding: 40px;
                        border-radius: 10px;
                        box-shadow: 0 10px 40px rgba(0,0,0,0.2);
                        text-align: center;
                        max-width: 600px;
                    }
                    h1 { color: #333; margin-bottom: 20px; font-size: 32px; }
                    .status {
                        background: #e8f5e9;
                        color: #2e7d32;
                        padding: 15px;
                        border-radius: 5px;
                        margin: 20px 0;
                        font-weight: bold;
                    }
                    .links {
                        margin-top: 30px;
                        display: flex;
                        gap: 15px;
                        justify-content: center;
                        flex-wrap: wrap;
                    }
                    a {
                        display: inline-block;
                        padding: 12px 24px;
                        margin: 5px;
                        text-decoration: none;
                        border-radius: 5px;
                        font-weight: bold;
                        transition: all 0.3s;
                    }
                    .btn-primary {
                        background: #667eea;
                        color: white;
                    }
                    .btn-primary:hover {
                        background: #764ba2;
                        transform: translateY(-2px);
                    }
                    .btn-secondary {
                        background: #f5f5f5;
                        color: #333;
                        border: 2px solid #667eea;
                    }
                    .btn-secondary:hover {
                        background: #667eea;
                        color: white;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>🎮 Connected Games Platform</h1>
                    <h2 style="color: #666; font-size: 18px; margin-bottom: 20px;">Central Server</h2>
                    <div class="status">✅ Server is ONLINE and RUNNING</div>
                    <div class="links">
                        <a href="/swagger-ui.html" class="btn-primary">📡 API Documentation</a>
                        <a href="/h2-console" class="btn-secondary">🗄️ Database Console</a>
                    </div>
                </div>
            </body>
            </html>
            """;
    }
}