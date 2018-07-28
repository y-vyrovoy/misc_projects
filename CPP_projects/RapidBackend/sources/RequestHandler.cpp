#include "stdafx.h"
#include "RequestHandler.h"

#include "Logger.h"

RequestHandler::RequestHandler()
{
}

RequestHandler::~RequestHandler()
{
	// TODO: check is the working thread avilve and stop it if necessary
}

void RequestHandler::Init( RequestQueue * pQueueManager,
							std::function<void(std::unique_ptr<ResponseData>)> responseCB)
{
	m_queueManager = pQueueManager;
	m_responseCallback = responseCB;
}

void RequestHandler::start()
{
	std::thread t([this]() { this->threadJob(); });
	m_workThread.swap(t);
}

void RequestHandler::stop()
{
	m_queueManager->stop_waiting();
	m_workThread.join();
}

void RequestHandler::threadJob()
{
	static const char * pNof = __FUNCTION__ ": ";

	while (true)
	{
		try {

			// Waitinig for the next request from the queue
			RequestPtr request = m_queueManager->pull();
			DebugLog << pNof << "Starting request processing: id=" << request->id << std::endl;


			//Here's the next request - let's send new response

			ResponsePtr response(new ResponseData);
			response->id = request->id;

			// TODO: getting response from ResponseCompiler
			
			const static char * pchMessage = "Default response message";
			
			memcpy(response->data.data(), pchMessage, std::min(response->data.size(), strlen(pchMessage) + 1) );

			m_responseCallback( std::move(response) );
			
		}
		catch (cTerminationException exTerm)
		{
			DebugLog << pNof << "terminating job" << std::endl;
			return;
		}
		catch (std::exception ex)
		{
			DebugLog << pNof << "Exception: "<< ex.what() << std::endl;
			return;
		}

	}
}